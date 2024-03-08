package com.example.authapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authapp.ui.theme.Bgcolor

import com.example.authapp.ui.theme.Primary


@Composable
fun TextField(labelValue: String, painter: Painter ) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(value = textValue.value,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp)),
        onValueChange = { textValue.value = it },
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = Bgcolor,
            focusedTextColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions.Default,
        leadingIcon = {
            Icon(painter = painter,
                contentDescription = "user leading icon",
                modifier = Modifier.size(15.dp),
                tint = Color.Black
            )
        }
    )
}

@Composable
fun PasswordTextField(labelValue: String, painter: Painter ) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(value = password.value,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp)),
        onValueChange = { password.value = it },
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = Bgcolor,
            focusedTextColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(painter = painter,
                contentDescription = "user leading icon",
                modifier = Modifier.size(15.dp),
                tint = Color.Black
            )
        },
        trailingIcon = {
            val visibilityIcon = if(passwordVisible.value) {
                Icons.Filled.Visibility
            }
            else{
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                "Hide Password"
            }
            else{
                "Show Password"
            }
            
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value){
            VisualTransformation.None
        }
        else{
            PasswordVisualTransformation()
        }

    )
}

@Composable
fun CheckBoxComponent(value:String, onTextSelected : (String) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(1.dp),
        verticalAlignment = Alignment.CenterVertically) {

        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkedState.value, onCheckedChange = {
            checkedState.value != checkedState.value
        })

        ClickableTextComponent(value = value,onTextSelected )
    }

}

@Composable
fun ButtonComponent(value: String){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(45.dp)
            .background(Color.Transparent)
    ) {
          Box(modifier = Modifier
              .fillMaxWidth()
              .heightIn(45.dp),
              contentAlignment = Alignment.Center){
              Text(text = value,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold)
          }
    }
}