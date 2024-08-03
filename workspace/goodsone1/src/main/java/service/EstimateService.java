package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import dao.AWSS3Dao;
import dao.EstimateDao;
import dto.EstimateDto;

@Service
public class EstimateService {


  private EstimateDao estimateDao;
  private  AWSS3Dao awss3Dao;
  
  @Autowired
  public EstimateService(EstimateDao estimateDao,AWSS3Dao awss3Dao) {
    this.estimateDao = estimateDao;
    this.awss3Dao = awss3Dao;
  }
  
  public void registerEstimate(EstimateDto estimateDto) throws SQLException, IOException {
    List<MultipartFile> reqImages = estimateDto.getImages();
    String imagesPath = awss3Dao.uploadImagesToS3(reqImages, estimateDto.getPhone());
    estimateDto.setImagesPath(imagesPath);
    // DAO로 DTO 전달
    estimateDao.registerEstimate(estimateDto);
  }

  
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
    int estimateSeq = Integer.parseInt(req.getParameter("estimateSeq"));
      String imagesPath = estimateDao.getImagesPath(estimateSeq);
      List<String> imageUrls = awss3Dao.getS3Urls(imagesPath);
      req.setAttribute("imageUrls", imageUrls);
      req.getRequestDispatcher("/displayImages.jsp").forward(req, res);
  }
  
  
}
