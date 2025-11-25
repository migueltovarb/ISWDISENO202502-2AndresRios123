package com.example.seminariapp.controller;

import com.example.seminariapp.model.Certificado;
import com.example.seminariapp.model.Evento;
import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.repository.EventoRepository;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.UsuarioRepository;
import com.example.seminariapp.service.CertificadoService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.util.Date;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public CertificadoController(CertificadoService certificadoService,
                                 InscripcionRepository inscripcionRepository,
                                 UsuarioRepository usuarioRepository,
                                 EventoRepository eventoRepository) {
        this.certificadoService = certificadoService;
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    // Generar certificado
    @PostMapping("/generar")
    public ResponseEntity<Certificado> generarCertificado(
            @RequestParam String inscripcionId
    ) {
        try {
            return ResponseEntity.ok(certificadoService.generarCertificado(inscripcionId));
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    // Buscar certificado por código QR (hash)
    @GetMapping("/validar/{qrHash}")
    public ResponseEntity<Certificado> obtenerPorQr(@PathVariable String qrHash) {
        Certificado certificado = certificadoService.obtenerPorQr(qrHash);
        return certificado != null ? ResponseEntity.ok(certificado) : ResponseEntity.notFound().build();
    }

    // Obtener certificado por inscripción
    @GetMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<Certificado> obtenerPorInscripcion(@PathVariable String inscripcionId) {
        Certificado certificado = certificadoService.obtenerPorInscripcion(inscripcionId);
        return certificado != null ? ResponseEntity.ok(certificado) : ResponseEntity.notFound().build();
    }

    // Descargar PDF del certificado (se genera si no existe)
    @GetMapping("/inscripcion/{inscripcionId}/pdf")
    public ResponseEntity<byte[]> obtenerPdf(@PathVariable String inscripcionId) throws DocumentException {
        Certificado certificado = certificadoService.obtenerPorInscripcion(inscripcionId);
        if (certificado == null) {
            certificado = certificadoService.generarCertificado(inscripcionId);
        }

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId).orElse(null);
        Usuario usuario = inscripcion != null ? usuarioRepository.findById(inscripcion.getUsuarioId()).orElse(null) : null;
        Evento evento = inscripcion != null ? eventoRepository.findById(inscripcion.getEventoId()).orElse(null) : null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

        Paragraph title = new Paragraph("Certificado de Asistencia", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
        doc.add(new Paragraph(" ", textFont));

        doc.add(new Paragraph("Otorgado a: " + (usuario != null ? usuario.getNombre() : "Usuario"), textFont));
        doc.add(new Paragraph("Correo: " + (usuario != null ? usuario.getEmail() : ""), textFont));
        doc.add(new Paragraph("Evento: " + (evento != null ? evento.getNombre() : "Evento"), textFont));
        doc.add(new Paragraph("Fecha de emisión: " + (certificado.getFechaEmision() != null ? certificado.getFechaEmision() : new Date()), textFont));
        doc.add(new Paragraph("Número de certificado: " + certificado.getNumero(), textFont));
        doc.add(new Paragraph("Código QR: " + certificado.getQrHash(), textFont));

        doc.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "certificado.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }
}
