package com.example.electric

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.electric.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
    }

    private fun initListener() {
        binding.edtU.doAfterTextChanged {
            calculator()
        }
        binding.edtI.doAfterTextChanged {
            calculator()
        }
        binding.edtR.doAfterTextChanged {
            calculator()
        }
        binding.edtP.doAfterTextChanged {
            calculator()
        }
    }

    private fun calculator() {
        val uInput = binding.edtU.value
        val iInput = binding.edtI.value
        val rInput = binding.edtR.value
        val pInput = binding.edtP.value

        val list = listOf(uInput, iInput, rInput, pInput)

        if (list.count { it.isNotEmpty() } == 2) {
            binding.tvError.gone()
            //calculator result
            /**calculate P - R*/
            if (uInput.isNotEmpty() && iInput.isNotEmpty()) {
                val u = uInput.toDouble()
                val i = iInput.toDouble()
                setTextU(u)
                setTextI(i)
                val p = u * i
                setTextP(p)
                if (i > 0) {
                    val r = (u / i)
                    setTextR(r)
                } else {
                    setTextR(0.0)
                }
                return
            }
            /**calculate R - I*/
            if (uInput.isNotEmpty() && pInput.isNotEmpty()) {
                val u = uInput.toDouble()
                val p = pInput.toDouble()
                setTextU(u)
                setTextP(p)
                if (u > 0) {
                    val i = p / u
                    setTextI(i)
                    val r = u / i
                    setTextR(r)
                } else {
                    setTextR(0.0)
                    setTextI(0.0)
                }
                return
            }
            /**calculate P - I*/
            if (uInput.isNotEmpty() && rInput.isNotEmpty()) {
                val u = uInput.toDouble()
                val r = rInput.toDouble()
                setTextU(u)
                setTextR(r)
                if (r > 0) {
                    val i = u / r
                    val p = r * i * i
                    setTextI(i)
                    setTextP(p)
                } else {
                    setTextI(0.0)
                    setTextP(0.0)
                }
                return
            }
            /**calculate R - U*/
            if (iInput.isNotEmpty() && pInput.isNotEmpty()) {

                val i = iInput.toDouble()
                val p = pInput.toDouble()
                setTextI(i)
                setTextP(p)
                if (i > 0) {
                    val u = p / i
                    setTextU(u)
                    val r = u / i
                    setTextR(r)
                } else {
                    setTextU(0.0)
                    setTextR(0.0)
                }
                return
            }
            /**calculate P - U*/
            if (iInput.isNotEmpty() && rInput.isNotEmpty()) {
                val i = iInput.toDouble()
                val r = rInput.toDouble()
                setTextI(i)
                setTextR(r)
                val u = i * r
                setTextU(u)
                val p = u * i
                setTextP(p)
                return
            }
            /**calculate U - I*/
            if (pInput.isNotEmpty() && rInput.isNotEmpty()) {
                val p = pInput.toDouble()
                val r = rInput.toDouble()
                 /**
                  * p = u *i
                  * u = r * i
                  * p = r * i * i
                  * */
                setTextP(p)
                setTextR(r)
                if (r > 0){
                    val i = sqrt(p/r)
                    val u = r * i
                    setTextU(u)
                    setTextI(i)
                }else{
                    setTextU(0.0)
                    setTextI(0.0)
                }
                return
            }
        } else {
            clear()
            binding.tvError.show()
        }
    }

    private fun clear() {
        binding.tvU.text = ""
        binding.tvI.text = ""
        binding.tvR.text = ""
        binding.tvP.text = ""
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    private fun View.gone() {
        this.visibility = View.GONE
    }

    private val EditText.value
        get() = this.text.toString()

    @SuppressLint("DefaultLocale")
    private fun Double.formatString() = String.format("%.3f", this)

    @SuppressLint("SetTextI18n")
    private fun setTextP(rs: Double) {
        binding.tvP.text = "P = ${rs.formatString()}W"
    }

    @SuppressLint("SetTextI18n")
    private fun setTextU(rs: Double) {
        binding.tvU.text = "U = ${rs.formatString()}V"
    }

    @SuppressLint("SetTextI18n")
    private fun setTextI(rs: Double) {
        binding.tvI.text = "I = ${rs.formatString()}A"
    }

    @SuppressLint("SetTextI18n")
    private fun setTextR(rs: Double) {
        binding.tvR.text = "R = ${rs.formatString()}Ω"
    }
}