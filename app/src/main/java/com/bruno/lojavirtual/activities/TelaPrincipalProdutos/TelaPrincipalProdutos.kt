package com.bruno.lojavirtual.activities.TelaPrincipalProdutos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.bruno.lojavirtual.R
import com.bruno.lojavirtual.activities.FormLogin.FormLogin
import com.bruno.lojavirtual.activities.Pedidos.Pedidos
import com.bruno.lojavirtual.adapter.AdapterProduto
import com.bruno.lojavirtual.databinding.ActivityTelaPrincipalProdutosBinding
import com.bruno.lojavirtual.dialog.DialogPerfilUsuario
import com.bruno.lojavirtual.model.DB
import com.bruno.lojavirtual.model.Produto

class TelaPrincipalProdutos : AppCompatActivity() {

    lateinit var binding: ActivityTelaPrincipalProdutosBinding
    lateinit var adapterProduto: AdapterProduto
    var lista_produtos: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_produtos = binding.recyclerProdutos
        recycler_produtos.layoutManager = GridLayoutManager(this,2)
        recycler_produtos.setHasFixedSize(true)
        adapterProduto = AdapterProduto(this,lista_produtos)
        recycler_produtos.adapter = adapterProduto

        val db = DB()
        db.obterListaDeProdutos(lista_produtos,adapterProduto)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.perfil -> iniciarDialogPerfilUsuario()
            R.id.pedidos -> iniciarTelaDePedidos()
            R.id.deslogar -> deslogarUsuario()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun iniciarDialogPerfilUsuario(){
        val dialogPerfilUsuario = DialogPerfilUsuario(this)
        dialogPerfilUsuario.iniciarPerfilUsuario()
        dialogPerfilUsuario.recuperarDadosUsuarioBanco()
    }

    private fun iniciarTelaDePedidos(){
     val intent = Intent(this,Pedidos::class.java)
     startActivity(intent)
    }

    private fun deslogarUsuario(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}