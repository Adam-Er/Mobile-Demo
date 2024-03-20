package com.delta.mobiledemo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delta.mobiledemo.ui.theme.DemoTheme
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.launch
import proto.CalculatorGrpcKt
import proto.calcRequest
import java.io.Closeable

class MainActivity : ComponentActivity() {
    private val uri: Uri = Uri.parse(BuildConfig.AUTHORITY)
    private val client by lazy {
        ClientRCP(uri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    RemoteAddButton(client)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}

class ClientRCP(uri: Uri) : Closeable {
    val responseState = mutableStateOf("")

    private val channel = let {
        Log.i(TAG, "Connecting to ${uri.host}:${uri.port}")

        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if (uri.scheme == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        builder.executor(Dispatchers.IO.asExecutor()).build()
    }

    private val client = CalculatorGrpcKt.CalculatorCoroutineStub(channel)

    suspend fun addNumbersRemote(numA: Int, numB: Int) {
        try {
            val request = calcRequest {
                this.numberA = numA
                this.numberB = numB
            }
            Log.i(TAG, "Sending request: $request")
            responseState.value = client.plus(request).result.toString()
        } catch (e: Exception) {
            responseState.value = e.message ?: "Unknown Error"
            e.printStackTrace()
        }
    }

    override fun close() {
        channel.shutdownNow()
    }

    companion object {
        val TAG: String = ClientRCP::class.java.simpleName
    }
}

@Composable
fun RemoteAddButton(clientRCP: ClientRCP) {
    val scope = rememberCoroutineScope()

    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(), Arrangement.Top, Alignment.CenterHorizontally) {
        Text("Add numbers", modifier = Modifier.padding(top = 10.dp))
        OutlinedTextField(textState.value, { textState.value = it })

        Button({ scope.launch { clientRCP.addNumbersRemote(2, 2) }}, Modifier.padding(10.dp)) {
            Text("Add numbers from api!")
        }

        if (clientRCP.responseState.value.isNotEmpty()) {
            Text("response", modifier = Modifier.padding(top = 10.dp))
            Text(clientRCP.responseState.value)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoTheme {
        Greeting("Android")
    }
}