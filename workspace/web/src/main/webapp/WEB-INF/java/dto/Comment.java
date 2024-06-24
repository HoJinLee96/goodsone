package dto;

import java.time.LocalDateTime;

public class Comment {
    private int commentSeq;
    private int userSeq;
    private Integer purchaseSeq;
    private Integer saleSeq;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer parentsSeq;

}
