package com.company.vote;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/25.
 * �Զ�������л��ͷ����л��Ľӿ�
 */
public interface VoteMsgCoder {
    /**
     * ���л�
     * @param msg
     * @return
     * @throws IOException
     */
    byte[] toWire(VoteMsg msg) throws IOException;

    /**
     * �����л�
     * @param input
     * @return
     * @throws IOException
     */
    VoteMsg fromWire(byte[] input) throws IOException;
}
