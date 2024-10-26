
import com.example.myapplication.Game
import com.example.myapplication.Riddle
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/RiddleGameAdmin/api.php")
    fun getRiddles(): Call<List<Game>>
}