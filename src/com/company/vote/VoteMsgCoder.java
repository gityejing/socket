package com.company.vote;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/25.
 * 自定义的序列化和反序列化的接口
 */
public interface VoteMsgCoder {
    /**
     * 序列化
     * @param msg
     * @return
     * @throws IOException
     */
    byte[] toWire(VoteMsg msg) throws IOException;

    /**
     * 反序列化
     * @param input
     * @return
     * @throws IOException
     */
    VoteMsg fromWire(byte[] input) throws IOException;
}
