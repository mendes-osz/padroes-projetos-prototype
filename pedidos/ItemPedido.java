package pedidos;

import java.math.BigDecimal;

public class ItemPedido {
    public String produto;
    public int quantidade;
    public BigDecimal precoUnitario;

    public ItemPedido(String produto, int quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemPedido(ItemPedido origem) {
        this.produto = origem.produto;
        this.quantidade = origem.quantidade;
        this.precoUnitario = origem.precoUnitario;
    }

    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}