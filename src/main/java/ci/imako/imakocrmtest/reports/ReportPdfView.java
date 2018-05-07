package ci.imako.imakocrmtest.reports;

import ci.imako.imakocrmtest.domain.Contact;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("contactView")
public class ReportPdfView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        Contact contact = (Contact) model.get("contact");

        //IText API
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document pdfDocument = new Document(pdf);

        //title
        Paragraph title = new Paragraph(contact.getNom());
        title.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        title.setFontSize(18f);
        title.setItalic();
        pdfDocument.add(title);

        //email
        Paragraph email = new Paragraph(contact.getEmail());
        title.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        title.setFontSize(18f);
        title.setItalic();
        pdfDocument.add(email);

        //telephone
        Paragraph telephone = new Paragraph(contact.getTelephone());
        title.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        title.setFontSize(18f);
        title.setItalic();
        pdfDocument.add(telephone);

        //telephone
        Paragraph categorie = new Paragraph(contact.getCategorie().name());
        title.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        title.setFontSize(18f);
        title.setItalic();
        pdfDocument.add(categorie);

        pdfDocument.close();

    }
}
