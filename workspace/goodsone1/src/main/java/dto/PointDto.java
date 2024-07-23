package dto;

import java.time.LocalDateTime;

public class Point {
//  private final String orderSeq;
//  private final String oderGoodsSeq;
  private final int point;
  private final LocalDateTime createAt;
  
  public Point(int point, LocalDateTime createAt) {
    this.point = point;
    this.createAt = createAt;
  }
  
}
