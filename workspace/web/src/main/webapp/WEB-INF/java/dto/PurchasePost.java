package dto;

import java.time.LocalDateTime;

public class PurchasePost {
    private int purchaseSeq;
    private int goodsSeq;
    private int userSeq;
    private String postTitle;
    private String postDescription;
    private int postAmount;
    private String goodsStatus;
    private int viewCnt;
    private int chatCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
