package com.company.vote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* Wire Format
 * 1 1 1 1 1 1
 * 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * | Magic |Flags| ZERO |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * | Candidate ID |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * | |
 * | Vote Count (only in response) |
 * | |
 * | |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 */
public class VoteMsgBinCoder implements VoteMsgCoder {

    // manifest constants for encoding
    public static final int MIN_WIRE_LENGTH = 4; // 数据长度最短4字节
    public static final int MAX_WIRE_LENGTH = 16; // 数据最长 16字节
    public static final int MAGIC = 0x5400;
    public static final int MAGIC_MASK = 0xfc00;
    public static final int MAGIC_SHIFT = 8;
    public static final int RESPONSE_FLAG = 0x0200;
    public static final int INQUIRE_FLAG = 0x0100;

    /**
     * 序列化
     * @param msg
     * @return
     * @throws IOException
     */
    @Override
    public byte[] toWire(VoteMsg msg) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream); // converts ints

        short magicAndFlags = MAGIC;
        // 如果是查询
        if (msg.isInquiry()) {
            magicAndFlags |= INQUIRE_FLAG;
        }

        // 如果是服务端反馈信息
        if (msg.isResponse()) {
            magicAndFlags |= RESPONSE_FLAG;
        }
        out.writeShort(magicAndFlags);
        // We know the candidate ID will fit in a short: it's > 0 && < 1000
        out.writeShort((short) msg.getCandidateID());
        if (msg.isResponse()) {
            out.writeLong(msg.getVoteCount());
        }
        out.flush();
        byte[] data = byteStream.toByteArray();
        return data;
    }

    /**
     * 反序列化
     * @param input
     * @return
     * @throws IOException
     */
    @Override
    public VoteMsg fromWire(byte[] input) throws IOException {
        // sanity checks
        if (input.length < MIN_WIRE_LENGTH) {
            throw new IOException("Runt message");
        }
        ByteArrayInputStream bs = new ByteArrayInputStream(input);
        DataInputStream in = new DataInputStream(bs);
        int magic = in.readShort();
        if ((magic & MAGIC_MASK) != MAGIC) {
            throw new IOException("Bad Magic #: " +
                    ((magic & MAGIC_MASK) >> MAGIC_SHIFT));
        }
        boolean resp = ((magic & RESPONSE_FLAG) != 0);
        boolean inq = ((magic & INQUIRE_FLAG) != 0);
        int candidateID = in.readShort();
        if (candidateID < 0 || candidateID > 1000) {
            throw new IOException("Bad candidate ID: " + candidateID);
        }
        long count = 0;
        if (resp) {
            count = in.readLong();
            if (count < 0) {
                throw new IOException("Bad vote count: " + count);
            }
        }
        // Ignore any extra bytes
        return new VoteMsg(resp, inq, candidateID, count);
    }
}