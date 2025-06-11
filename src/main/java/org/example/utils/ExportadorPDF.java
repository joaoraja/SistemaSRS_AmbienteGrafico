package org.example.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.example.models.Aluno;
import org.example.models.RegistroHistorico;
import org.example.models.Secao;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportadorPDF {

    // Geração temporária de boletim para envio por e-mail
    public static File gerarBoletimPDF(Aluno aluno) {
        try {
            File arquivo = File.createTempFile("boletim_" + aluno.getNome().replace(" ", "_"), ".pdf");

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(arquivo));
            doc.open();

            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Boletim Escolar", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Nome: " + aluno.getNome()));
            doc.add(new Paragraph("CPF: " + aluno.getCpf()));
            doc.add(new Paragraph("Curso: " + aluno.getCurso() + " (" + aluno.getGrau() + ")"));
            doc.add(Chunk.NEWLINE);

            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            tabela.addCell(new PdfPCell(new Phrase("Código", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Disciplina", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Nota", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Semestre", headerFont)));

            List<RegistroHistorico> registros = aluno.getHistorico().getRegistros();
            for (RegistroHistorico registro : registros) {
                Secao s = registro.getSecao();
                tabela.addCell(s.getCursoRepresentado().getCodigoCurso());
                tabela.addCell(s.getCursoRepresentado().getNomeCurso());
                tabela.addCell(registro.getNota());
                tabela.addCell(s.getOfertadaEm().getSemestre());
            }

            doc.add(tabela);
            doc.close();
            return arquivo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Exportação manual via JFileChooser
    public static void exportarBoletim(Aluno aluno, String caminhoArquivo) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Boletim Escolar", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            Font fontPadrao = new Font(Font.FontFamily.HELVETICA, 12);
            document.add(new Paragraph("Nome: " + aluno.getNome(), fontPadrao));
            document.add(new Paragraph("CPF: " + aluno.getCpf(), fontPadrao));
            document.add(new Paragraph("Curso: " + aluno.getCurso() + " (" + aluno.getGrau() + ")", fontPadrao));
            document.add(Chunk.NEWLINE);

            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            tabela.addCell(new PdfPCell(new Phrase("Código", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Disciplina", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Nota", headerFont)));
            tabela.addCell(new PdfPCell(new Phrase("Semestre", headerFont)));

            List<RegistroHistorico> registros = aluno.getHistorico().getRegistros();
            if (registros.isEmpty()) {
                document.add(new Paragraph("Nenhuma disciplina encontrada."));
            } else {
                for (RegistroHistorico registro : registros) {
                    Secao s = registro.getSecao();
                    tabela.addCell(s.getCursoRepresentado().getCodigoCurso());
                    tabela.addCell(s.getCursoRepresentado().getNomeCurso());
                    tabela.addCell(registro.getNota());
                    tabela.addCell(s.getOfertadaEm().getSemestre());
                }
                document.add(tabela);
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}