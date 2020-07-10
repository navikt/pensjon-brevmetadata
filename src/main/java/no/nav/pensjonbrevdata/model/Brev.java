package no.nav.pensjonbrevdata.model;

import no.nav.pensjonbrevdata.json.JSONList;
import no.nav.pensjonbrevdata.json.JSONVisitor;
import no.nav.pensjonbrevdata.model.codes.*;

import java.util.List;

public abstract class Brev extends Brevdata {
    private boolean redigerbart;
    private String dekode;
    private BrevkategoriCode brevkategori;
    private DokumenttypeCode dokType;
    private List<SprakCode> sprak;
    private Boolean visIPselv;
    private BrevUtlandCode utland;
    private BrevregeltypeCode brevregeltype;
    private BrevkravtypeCode brevkravtype;
    private BrevkontekstCode brevkontekst;
    private DokumentkategoriCode dokumentkategori;
    private Boolean synligForVeileder;
    private Integer prioritet;


    public Brev(String brevkodeInBrevsystem,
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
                BrevsystemCode brevsystem,
                Integer prioritet
    ) {
        super(brevkodeInBrevsystem, brevsystem);
        this.redigerbart = redigerbart;
        this.dekode = dekode;
        this.brevkategori = brevkategori;
        this.dokType = doktype;
        this.sprak = sprak;
        this.visIPselv = visIPselv;
        this.utland = utland;
        this.brevregeltype = brevregeltype;
        this.brevkravtype = brevkravtype;
        this.dokumentkategori = dokumentkategori;
        this.synligForVeileder = synligForVeileder;
        this.brevkontekst = brevkontekst;
        this.prioritet = prioritet;
    }

    public List<SprakCode> getSprak() {
        return sprak;
    }

    @Override
    public JSONVisitor visit(JSONVisitor jsonVisitor) {
        return super.visit(jsonVisitor.field("redigerbart",redigerbart)
                .field("dekode", dekode)
                .field("brevkategori", brevkategori)
                .field("dokType", dokType)
                .field("sprak", JSONList.jsonIfiableList(sprak))
                .field("visIPselv", visIPselv)
                .field("utland", utland)
                .field("brevregeltype", brevregeltype)
                .field("brevkravtype", brevkravtype)
                .field("brevkontekst", brevkontekst)
                .field("dokumentkategori", dokumentkategori)
                .field("synligForVeileder", synligForVeileder)
                .field("prioritet", prioritet));
    }
}
