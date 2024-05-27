package com.example.nileassociate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.nileassociate.presentation.MapsComponent
import com.example.nileassociate.ui.theme.NileAssociateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NileAssociateTheme {
                Surface {
                    MapsComponent()
                }
            }
        }
    }
}
