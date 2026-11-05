package pedidos;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PedidosApp {

    public static void main(String[] args) {
        Pedido modelo = new Pedido("(modelo)", "-");
        modelo.itens.add(new ItemPedido("Café 500g", 10, new BigDecimal("18.90")));
        modelo.itens.add(new ItemPedido("Açúcar 1kg", 20, new BigDecimal("4.50")));
        modelo.itens.add(new ItemPedido("Filtro de papel", 5, new BigDecimal("7.20")));

        CatalogoDePedidos catalogo = new CatalogoDePedidos();
        catalogo.registrar("reposicao-mensal", modelo);

        Pedido a = catalogo.criar("reposicao-mensal");
        a.cliente = "Mercado Central";
        a.enderecoEntrega = "Rua das Flores, 120";

        Pedido b = catalogo.criar("reposicao-mensal");
        b.cliente = "Padaria Boa Massa";
        b.enderecoEntrega = "Av. Paraná, 800";
        b.itens.get(0).quantidade = 20;

        Pedido c = catalogo.criar("reposicao-mensal");
        c.cliente = "Restaurante do Porto";
        c.enderecoEntrega = "Rua do Cais, 45";
        c.itens.add(new ItemPedido("Adoçante 100ml", 12, new BigDecimal("6.00")));

        for (Pedido p : List.of(modelo, a, b, c)) {
            System.out.println("Pedido #" + p.id + " | " + p.cliente + " | " + p.enderecoEntrega
                    + " | itens=" + p.itens.size() + " | total=R$ "
                    + p.valorTotal().toString().replace('.', ','));
        }

        boolean lancou = false;
        try {
            catalogo.criar("inexistente");
        } catch (IllegalArgumentException e) {
            lancou = true;
        }

        verificar("CA-01 ids diferentes", new HashSet<>(List.of(modelo.id, a.id, b.id, c.id)).size() == 4);
        verificar("CA-02 modelo continua R$ 315,00", modelo.valorTotal().toString().equals("315.00"));
        verificar("CA-03 totais A=315,00 B=504,00 C=387,00", a.valorTotal().toString().equals("315.00")
                && b.valorTotal().toString().equals("504.00") && c.valorTotal().toString().equals("387.00"));
        verificar("CA-04 modelo continua com 3 itens", modelo.itens.size() == 3);
        verificar("CA-05 nenhum item das cópias é do modelo", Collections.disjoint(modelo.itens, a.itens)
                && Collections.disjoint(modelo.itens, b.itens) && Collections.disjoint(modelo.itens, c.itens));
        verificar("CA-06 criar(\"inexistente\") lança IllegalArgumentException", lancou);
        verificar("CA-07 cliente do modelo continua \"(modelo)\"", modelo.cliente.equals("(modelo)"));
    }

    static void verificar(String descricao, boolean ok) {
        System.out.println(descricao + ": " + (ok ? "OK" : "FALHA"));
    }
}