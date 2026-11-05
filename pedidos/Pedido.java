package pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Prototype<Pedido> {
    public static long proximoId = 1;

    public long id;
    public String cliente;
    public String enderecoEntrega;
    public List<ItemPedido> itens = new ArrayList<>();
    public LocalDate criadoEm;

    public Pedido(String cliente, String enderecoEntrega) {
        this.id = proximoId++;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
        this.criadoEm = LocalDate.now();
    }

    @Override
    public Pedido clonar() {
        Pedido copia = new Pedido(this.cliente, this.enderecoEntrega);
        for (ItemPedido item : this.itens) {
            copia.itens.add(new ItemPedido(item));
        }
        return copia;
    }

    public BigDecimal valorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.subtotal());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}