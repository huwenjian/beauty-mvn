
object test {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = newSparkConf
    val sc = new SparkContext(conf)
    sc.textFile("C:\\Users\\huwenjian\\Desktop\\data\\credit-xiaomi.json")
      .foreach {
        line =>
          val hello = JSON.parseObject(JSON.parseObject(line).get("_source").toString).get("Name")
          println(hello.toString)
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
}
