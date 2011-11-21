/*Consulta de mesas abertas e com pedidos 
*/
select me.mesa_id, me.numero, me.status, pe.pedido_id, i_p.qtdade, pr.descricao
from mesa me
inner join mesa_ocupacao m_o on
  me.mesa_id = m_o.mesa_id
inner join pedido pe on
  pe.mesa_ocupacao_id = m_o.mesa_ocupacao_id
inner join item_pedido i_p on
  pe.pedido_id = i_p.pedido_id
inner join produto pr on
  i_p.produto_id = pr.produto_id
where me.status = 1 and m_o.fechamento is null;
