package com.lagou.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;
    private Serializer serializer;

    public RpcDecoder(Class<?> clazz, Serializer serializer) {

        this.clazz = clazz;

        this.serializer = serializer;

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {  //这个HEAD_LENGTH是我们用于表示头长度的字节数。  由于上面我们传的是一个int类型的值，所以这里HEAD_LENGTH的值为4.
            return;
        }
        byteBuf.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        int dataLength = byteBuf.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
        if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            channelHandlerContext.close();
        }

        if (byteBuf.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] body = new byte[dataLength];  //  嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
        byteBuf.readBytes(body);  //
        Object o = convertToObject(body);  //将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择
        list.add(o);
    }

    private RpcRequest convertToObject(byte[] body) throws IOException {
        return (RpcRequest) serializer.deserialize(clazz, body);
    }
}
