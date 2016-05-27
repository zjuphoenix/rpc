package com.middleware.study.rpc.transport.impl;

import com.middleware.study.rpc.transport.common.Constants;
import com.middleware.study.rpc.transport.api.Serialization;
import com.middleware.study.rpc.transport.exception.FrameworkException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.*;
import java.util.List;

/**
 * @author wuhaitao
 * @date 2016/5/26 13:32
 */
public class RpcCodecAdapter {
    private Serialization serialization;

    public RpcCodecAdapter(Serialization serialization) {
        this.serialization = serialization;
    }

    public MessageToByteEncoder<Object> encoder(){
        return new MessageToByteEncoder<Object>(){

            @Override
            protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutput output = new ObjectOutputStream(outputStream);
                if (o instanceof RpcRequest){
                    RpcRequest request = (RpcRequest) o;
                    output.writeByte(Constants.FLAG_REQUEST);
                    output.writeUTF(request.getInterfaceName());
                    output.writeUTF(request.methodName());
                    String[] paramsType = request.getParamtersType();
                    int paramNum = 0;
                    if (paramsType != null){
                        paramNum = paramsType.length;
                    }
                    output.writeInt(paramNum);
                    for (String param : paramsType) {
                        output.writeUTF(param);
                    }
                    Object[] params = request.getParamters();
                    if (params == null || params.length != paramNum){
                        throw new FrameworkException("paramsType and params not match!");
                    }
                    for (Object obj : params){
                        output.writeObject(serialization.serialize(obj));
                    }
                    output.writeLong(request.getRequestId());
                    output.flush();
                    byte[] body = outputStream.toByteArray();
                    output.close();
                    byteBuf.writeBytes(body);
                }
                else if(o instanceof RpcResponse){
                    RpcResponse response = (RpcResponse) o;
                    output.writeByte(Constants.FLAG_RESPONSE);
                    output.writeLong(response.getRequestId());
                    if (response.getException() != null){
                        output.writeByte(Constants.FLAG_EXCEPTION_RESPONSE);
                        output.writeUTF(response.getException().getClass().getName());
                        output.writeObject(serialization.serialize(response.getException()));
                    }
                    else if(response.getValue() == null){
                        output.writeByte(Constants.FLAG_VOID_RESPONSE);
                    }
                    else{
                        output.writeByte(Constants.FLAG_NORMAL_RESPONSE);
                        output.writeUTF(response.getValue().getClass().getName());
                        output.writeObject(serialization.serialize(response.getValue()));
                    }
                    output.flush();
                    byteBuf.writeBytes(outputStream.toByteArray());
                    output.close();
                }
                else{
                    output.close();
                    throw new FrameworkException("message not support while encoding!");
                }

            }
        };
    }

    public MessageToMessageDecoder<ByteBuf> decoder(){
        return new MessageToMessageDecoder<ByteBuf>(){

            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                final int length = byteBuf.readableBytes();
                final byte[] bytes = new byte[length];
                byteBuf.getBytes(byteBuf.readerIndex(), bytes, 0, length);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                ObjectInput input = new ObjectInputStream(inputStream);
                byte type = input.readByte();
                if (type == Constants.FLAG_REQUEST){
                    RpcRequest request = new RpcRequest();
                    String interfaceName = input.readUTF();
                    request.setInterfaceName(interfaceName);
                    String methodName = input.readUTF();
                    request.setMethodName(methodName);
                    int paramNum = input.readInt();
                    String[] paramType = new String[paramNum];
                    Object[] params = new Object[paramNum];
                    for (int i = 0; i < paramNum; i++) {
                        paramType[i] = input.readUTF();
                    }
                    request.setParamtersType(paramType);
                    for (int i = 0; i < paramNum; i++) {
                        params[i] = serialization.deserialize((byte[])input.readObject(), Class.forName(paramType[i]));
                    }
                    request.setParamters(params);
                    request.setRequestId(input.readLong());
                    list.add(request);
                    input.close();
                }
                else if (type == Constants.FLAG_RESPONSE){
                    RpcResponse response = new RpcResponse();
                    response.setRequestId(input.readLong());
                    byte returnType = input.readByte();
                    if (returnType == Constants.FLAG_EXCEPTION_RESPONSE){
                        String exceptionType = input.readUTF();
                        byte[] exception = (byte[])input.readObject();
                        response.setException((Exception)serialization.deserialize(exception, Class.forName(exceptionType)));
                    }
                    else if(returnType == Constants.FLAG_VOID_RESPONSE){
                        response.setValue(null);
                    }
                    else{
                        String valueType = input.readUTF();
                        byte[] value = (byte[])input.readObject();
                        response.setValue(serialization.deserialize(value, Class.forName(valueType)));
                    }
                    list.add(response);
                    input.close();
                }
                else{
                    input.close();
                    throw new FrameworkException("message type not support while decoding!");
                }

            }
        };
    }

}
