package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.helpers.XsdFileReader;
import no.nav.pensjonbrevdata.json.JSONList;
import no.nav.pensjonbrevdata.json.JSONVisitor;
import no.nav.pensjonbrevdata.model.codes.*;

import java.io.*;
import java.util.List;

public class Doksysbrev extends Brev {
    private List<DoksysVedlegg> vedleggListe;
    private String dokumentmalId;
    private String dokumentmalFelleselementId;
    private String dokumentmal;
    private String dokumentmalFelleselement;

    public Doksysbrev(String brevkodeInBrevsystem,
                      boolean redigerbart,
                      String dekode,
                      BrevkategoriCode brevkategori,
                      DokumenttypeCode doktype,
                      List<SprakCode> sprak,
                      Boolean visIPselv,
                      BrevUtlandCode utland,
                      BrevregeltypeCode brevregeltype,
                      BrevkravtypeCode brevkravtype,
                      DokumentkategoriCode dokumentkategori,
                      Boolean synligForVeileder,
                      BrevkontekstCode brevkontekst,
                      Integer prioritet,
                      String dokumentmalId,
                      String dokumentmalFelleselementId,
                      List<DoksysVedlegg> vedleggListe) {
        super(brevkodeInBrevsystem,
                redigerbart,
                dekode,
                brevkategori,
                doktype,
                sprak,
                visIPselv,
                utland,
                brevregeltype,
                brevkravtype,
                dokumentkategori,
                synligForVeileder,
                brevkontekst,
                BrevsystemCode.DOKSYS,
                prioritet);

        this.vedleggListe = vedleggListe;
        this.dokumentmalId = dokumentmalId;
        this.dokumentmalFelleselementId = dokumentmalFelleselementId;
    }

    public void generateDokumentmalFromFile() throws IOException {
//        DETTE FUNGERTE IKKE I DOCKER, MEN FUNGERTE UTENFOR DOCKER:
//        File dokumentmalFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
//        dokumentmal = new String(Files.readAllBytes(Paths.get(dokumentmalFile.getPath())));
//        File dokumentMalFelleselementFile = ResourceUtils.getFile("classpath:xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
//        dokumentmalFelleselement = new String(Files.readAllBytes(Paths.get(dokumentMalFelleselementFile.getPath())));
        XsdFileReader fileReader = new XsdFileReader();
        dokumentmal = fileReader.read("xsd" + File.separator + "dokumentmal" + File.separator + dokumentmalId + ".xsd");
        dokumentmalFelleselement = fileReader.read("xsd" + File.separator + "felles" + File.separator + dokumentmalFelleselementId + ".xsd");
        if (vedleggListe != null) {
            for (DoksysVedlegg vedlegg : vedleggListe) {
                vedlegg.generateDokumentmalFromFile();
            }
        }
    }

    @Override
    public JSONVisitor visit(JSONVisitor jsonVisitor) {
        return super.visit(jsonVisitor.field("vedleggListe", JSONList.jsonVisitableList(vedleggListe))
                .field("dokumentmalId",dokumentmalId)
                .field("dokumentmalFelleselementId",dokumentmalFelleselementId)
                .field("dokumentmal",dokumentmal)
                .field("dokumentmalFelleselement",dokumentmalFelleselement));
    }
}
