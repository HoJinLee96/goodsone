package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class S3Service {
  
  private static final String S3_BUCKET_URL = "https://your-bucket-name.s3.your-region.amazonaws.com/";

  public static List<String> getS3Urls(String imagesPath) {
      String[] paths = imagesPath.split(",");
      List<String> urls = new ArrayList<>();
      for (String path : paths) {
          urls.add(S3_BUCKET_URL + path);
      }
      return urls;
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int estimateSeq = Integer.parseInt(request.getParameter("estimateSeq"));
    try {
        String imagesPath = estimateDAO.getImagesPath(estimateSeq);
        List<String> imageUrls = S3Helper.getS3Urls(imagesPath);
        request.setAttribute("imageUrls", imageUrls);
        request.getRequestDispatcher("/displayImages.jsp").forward(request, response);
    } catch (Exception e) {
        throw new ServletException("Error retrieving images", e);
    }
}
  
}
