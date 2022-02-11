package br.com.tcp.servidor.app;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

public class Recebimento extends ChannelInboundHandlerAdapter {

    private static String ack = "\u0006";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            String ip = ctx.channel().remoteAddress().toString().replace("/", "");
            byte[] dados = byteParaString((ByteBuf) msg);
            Processamento processo = new Processamento();
            String evento = new String(dados);
            mostrarMensagemRecebida(ctx, ip, processo, evento);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static String timeStamp() {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        return (data + " " + hora + " = ");
    }
    public void enviarAck(ChannelHandlerContext ctx){
        ByteBuf out = ctx.alloc().buffer(ack.length()*2);
        out.writeBytes(ack.getBytes());
        ctx.writeAndFlush(out);
    }
    private void mostrarMensagemRecebida(ChannelHandlerContext ctx, String ip, Processamento processo, String evento) {
        // 50000022566325563

        if (evento.contains("500000 ")) {
            System.out.println(timeStamp() + "{" + ip + "} = Recebeu do Servidor -> " + processo.decode(evento.substring(7)));
            enviarAck(ctx);
        } else if (evento.contains("5000 ")) {
            System.out.println(timeStamp() + "{" + ip + "} = Recebeu do Cliente -> " + processo.decode(evento.substring(5)));
            enviarAck(ctx);
        } else if (evento.contains("1011 ")) {
            System.out.println(timeStamp() + "{" + ip + "} = Recebeu um Keep Alive -> " + evento);
            enviarAck(ctx);
        } else {
            System.out.println(timeStamp() + "{" + ip + "} = Evento sem formatação -> " + evento);
            enviarAck(ctx);
        }
    }
    private byte[] byteParaString(ByteBuf msg) {
        ByteBuf byteBuf = msg;
        byte[] dados;
        int length = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            dados = byteBuf.array();
        } else {
            dados = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), dados);
        }
        return dados;
    }
}
