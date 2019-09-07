# NewsApp
1.MVP
2.SharedAnimation
3.Room (insert,delete) @TypeConverters for Room
4.Retrofit
5.Mokito
6.RxJava


//@TypeConverters
@Entity(tableName = "tbl_newsData")
data class Article(
    @PrimaryKey(autoGenerate = true) var _id: Long?,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "url") var url: String?,
    @ColumnInfo(name = "urlToImage") var urlToImage: String?,
    @ColumnInfo(name = "publishedAt") var publishedAt: String?,
    @ColumnInfo(name = "content") var content: String?,
    @TypeConverters(SourceTypeConverter::class)
    @ColumnInfo(name = "source")
    var source: Source?
){
    class SourceTypeConverter {
        @TypeConverter
        fun fromSourceList(source: Source?): String? {
            if (source == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<Source>() {

            }.type
            return gson.toJson(source, type)
        }

        @TypeConverter
        fun toSourceList(source: String?): Source? {
            if (source == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<Source>() {

            }.type
            return gson.fromJson(source, type)
        }
    }

    constructor() : this(null,"", "", "",
        "", "", "", "",null)
}
