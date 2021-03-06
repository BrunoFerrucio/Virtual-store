package com.bruno.lojavirtual.activities.FormCadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.bruno.lojavirtual.databinding.ActivityFormCadastroBinding
import com.bruno.lojavirtual.model.DB

class FormCadastro : AppCompatActivity() {

    lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val db = DB()

        binding.btCadastrar.setOnClickListener {

            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(it,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
               FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener { tarefa ->
                   if (tarefa.isSuccessful){
                       db.salvarDadosUsuario(nome)
                       val snackbar = Snackbar.make(it,"Cadastro realizado com sucesso!",Snackbar.LENGTH_SHORT)
                       snackbar.setBackgroundTint(Color.BLUE)
                       snackbar.setTextColor(Color.WHITE)
                       snackbar.show()
                   }
               }.addOnFailureListener { erroCadastro ->

                   val mensagemErro = when(erroCadastro){
                       is FirebaseAuthWeakPasswordException -> "Digite uma senha com no m??nimo 6 caracteres"
                       is FirebaseAuthUserCollisionException -> "Esta conta j?? foi cadastrada"
                       is FirebaseNetworkException -> "Sem conex??o com a internet"
                       else -> "Erro ao cadastrar usu??rio"
                   }
                   val snackbar = Snackbar.make(it,mensagemErro,Snackbar.LENGTH_SHORT)
                   snackbar.setBackgroundTint(Color.RED)
                   snackbar.setTextColor(Color.WHITE)
                   snackbar.show()
               }
            }
        }

    }
}