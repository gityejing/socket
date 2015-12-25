package com.company;

/**
 * Created by Administrator on 2015/12/25.
 */
public class VoteMsg {
    private boolean isInquiry; // true:��ѯ false:ͶƱ
    private boolean isResponse;// true:����˷�����Ӧ
    private int candidateID; // in [0,1000] ��ѡ�˵ı��
    private long voteCount; // ͶƱ�Ĵ���

    public static final int MAX_CANDIDATE_ID = 1000;

    /**
     * ���췽��
     * @param isResponse �Ƿ�����Ӧ
     * @param isInquiry �Ƿ��ǲ�ѯ������ͶƱ����
     * @param candidateID ��ѡ�˵�id
     * @param voteCount �ܵ�ͶƱ����
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
