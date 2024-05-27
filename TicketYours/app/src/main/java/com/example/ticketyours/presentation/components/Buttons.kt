package com.example.ticketyours.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilledButton(text: String,onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A86B)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OutlinedButton(text: String,onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF00A86B)),
        border = BorderStroke(1.dp, Color(0xFF00A86B)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SideButton(imageVector: ImageVector,modifier: Modifier,Description: String,onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxHeight(0.92f),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent,
            contentColor = Color.Black)) {
        Icon(imageVector = imageVector,contentDescription = Description)
    }
}