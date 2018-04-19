import com.alibaba.fastjson.JSON
import org.apache.spark.{SparkConf, SparkContext}

object coreStart {


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = newSparkConf
    val sc = new SparkContext(conf)
    val lines = sc.textFile("C:\\Users\\崔傅成\\Desktop\\data\\data\\credit-xiaomi.json")
    lines.foreach {
      line =>
        val hello = JSON.parseObject(JSON.parseObject(line).get("_source").toString).get("Name")


        val h = SegmentWords.segment(hello.toString)
    }

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

  def removeRash(name: String): Unit = {
    if (name.contains("农民专业合作社")) {
      println(name)
    }
  }
}
