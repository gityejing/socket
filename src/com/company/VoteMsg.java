package com.company;

/**
 * Created by Administrator on 2015/12/25.
 */
public class VoteMsg {
    private boolean isInquiry; // true:查询 false:投票
    private boolean isResponse;// true:服务端返回响应
    private int candidateID; // in [0,1000] 候选人的编号
    private long voteCount; // 投票的次数

    public static final int MAX_CANDIDATE_ID = 1000;

    /**
     * 构造方法
     * @param isResponse 是否是响应
     * @param isInquiry 是否是查询请求还是投票操作
     * @param candidateID 候选人的id
     * @param voteCount 总的投票次数
     * @throws IllegalArgumentException
     */
    public VoteMsg(boolean isResponse, boolean isInquiry,
                   int candidateID, long voteCount)
            throws IllegalArgumentException {
        // check invariants
        if (voteCount != 0 && !isResponse) {
            throw new IllegalArgumentException("Request vote count must be zero");
        }
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("Bad Candidate ID: " + candidateID);
        }
        if (voteCount < 0) {
            throw new IllegalArgumentException("Total voteCount must be >= zero");
        }

        this.candidateID = candidateID;
        this.isResponse = isResponse;
        this.isInquiry = isInquiry;
        this.voteCount = voteCount;
    }

    public void setInquiry(boolean isInquiry) {
        this.isInquiry = isInquiry;
    }

    public void setResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setCandidateID(int candidateID) throws
            IllegalArgumentException {
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("Bad Candidate ID: " + candidateID);
        }
        this.candidateID = candidateID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setVoteCount(long count) {
        if ((count != 0 && !isResponse) || count < 0) {
            throw new IllegalArgumentException("Bad vote count");
        }
        voteCount = count;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public String toString() {
        String res = (isInquiry ? "inquiry" : "vote") + "for candidate " + candidateID;
        if (isResponse) {
            res = "response to " + res + " who now has " + voteCount + " vote(s)";
        }
        return res;
    }
}
