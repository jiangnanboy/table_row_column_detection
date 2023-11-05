package sy;

import ai.onnxruntime.OrtException;
import com.alibaba.fastjson2.JSON;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import utils.PropertiesReader;

import java.util.List;

/**
 * @author sy
 * @date 2023/5/2 15:49
 */
public class MainTest {
        public static void main(String...args) {
        String modelPath = MainTest.class.getClassLoader().getResource(PropertiesReader.get("model_path")).getPath().replaceFirst("/", "");
        String labelPath = MainTest.class.getClassLoader().getResource(PropertiesReader.get("table_det_labels_path")).getPath().replaceFirst("/", "");
        String imgPath = "D:\\project\\idea_workspace\\table_row_column_detection\\img\\test.jpeg";

        try {
            ModelDet modelDet = new ModelDet(modelPath, labelPath);
            Mat img = Imgcodecs.imread(imgPath);
            if (img.dataAddr() == 0) {
                System.out.println("Could not open image: " + imgPath);
                System.exit(1);
            }
            // run detection
            try {
                List<Detection> detectionList = modelDet.detectObjects(img);

                ImageUtil.drawPredictions(img, detectionList);
                System.out.println(JSON.toJSONString(detectionList));
                Imgcodecs.imwrite("D:\\project\\idea_workspace\\table_row_column_detection\\img\\prediction.jpg", img);
            } catch (OrtException ortException) {
                ortException.printStackTrace();
            }

        } catch (OrtException e) {
            e.printStackTrace();
        }
    }

}
