import com.alibaba.fastjson.JSON
import org.apache.spark.{SparkConf, SparkContext}

object coreStart {


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = newSparkConf
    val sc = new SparkContext(conf)
    val lines = sc.textFile("C:\\Users\\崔傅成\\Desktop\\data\\data\\credit-xiaomi2.json")
    lines.map {
      line =>
        val name = JSON.parseObject(JSON.parseObject(line).get("_source").toString).get("Name")
        val money = JSON.parseObject(JSON.parseObject(line).get("_source").toString).get("regist_capi")
        if (money != null && !money.equals("")) {
          val money2 = money.toString.toDouble
          if (money2 > 1000) {
            val coreName = SegmentWords.getCoreName(name.toString)

          }
        }
    }
         .saveAsTextFile("C:\\Users\\崔傅成\\Desktop\\data\\data\\result")



    sc.stop()
  }

  private def newSparkConf = {
    val conf = new SparkConf().setMaster("local[3]")
      .setAppName("Spark Pi")
      .set("spark.ui.port", "9995")
      .set("spark.driver.cores", "3")
      .set("spark.driver.memory", "1g")
      .set("spark.executor.memory", "2g")
      .set("spark.rdd.compress", "true")
    conf
  }
}
