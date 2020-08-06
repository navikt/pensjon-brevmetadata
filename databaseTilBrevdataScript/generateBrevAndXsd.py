import csv

def getSprakForBrev(brevkode):
    with open('inputfiler/sprakForBrev.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        sprakList = []
        for row in readCSV:
            if(row[0]==brevkode):
                sprakList.append(row[1])
        if(len(sprakList)==0):
            return "null"
        brevString = "Arrays.asList("
        for i in range(0, len(sprakList)):
            brevString+="SprakCode."+sprakList[i]
            if(i==len(sprakList)-1):
                brevString+=")"
            else:
                brevString+=", "
        return brevString  

def nullCheck(startOfExpression, endOfExpression):
    if(endOfExpression=="" or endOfExpression=="\"\""):
        return "null"
    else:
        return startOfExpression+endOfExpression

def booleanConvert(boolean):
    if(boolean=="1"):
        return "true"
    elif(boolean=="0"):
        return "false"
    else:
        return "null"

def getVedleggForBrev(brevkode):
    with open('inputfiler/vedleggMappingsSortedByRank.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        vedleggList = []
        for row in readCSV:
            if(row[0]==brevkode):
                vedleggList.append(row[1])
        if(len(vedleggList)==0):
            return "null"
        vedleggString = "doksysVedleggMapper.map("
        for i in range(0, len(vedleggList)):
            vedleggString+="\""+vedleggList[i]+"\""
            if(i==len(vedleggList)-1):
                vedleggString+=")"
            else:
                vedleggString+=", "
        return vedleggString  

def createGammeltBrev(brevkode, brevkodeInBrevsystem, redigerbart, dekode, brevkategori, dokumenttype, utland, visIPselv, regeltype, kravtype, dokumentkategori, synligForVeileder, brevkontekst, prioritet, brevgruppe):
     putBrev = ("brevMap.put(\""+brevkode+"\", () ->\n\tnew GammeltBrev(\""+brevkodeInBrevsystem+"\",\n\t\t"+
     booleanConvert(nullCheck("", redigerbart))+",\n\t\t"+
     nullCheck("", "\""+dekode+"\"")+",\n\t\t"+
     nullCheck("BrevkategoriCode.", brevkategori)+",\n\t\t"+
     nullCheck("DokumenttypeCode.", dokumenttype)+",\n\t\t"+
     getSprakForBrev(brevkodeInBrevsystem)+",\n\t\t"+
     booleanConvert(nullCheck("",visIPselv))+",\n\t\t"+
     nullCheck("BrevUtlandCode.", utland)+",\n\t\t"+
     nullCheck("BrevregeltypeCode.", regeltype)+",\n\t\t"+
     nullCheck("BrevkravtypeCode.", kravtype)+",\n\t\t"+
     nullCheck("DokumentkategoriCode.", dokumentkategori)+",\n\t\t"+
     booleanConvert(nullCheck("",synligForVeileder))+",\n\t\t"+
     nullCheck("BrevkontekstCode.", brevkontekst)+",\n\t\t"+
     nullCheck("", prioritet)+",\n\t\t"+
     "\""+brevgruppe+"\"));")
     return putBrev

def createDoksysBrev(brevkode, brevkodeInBrevsystem, redigerbart, dekode, brevkategori, dokumenttype, utland, visIPselv, regeltype, kravtype, dokumentkategori, synligForVeileder, brevkontekst, prioritet, dokumentmalId, felleselementId):
     putBrev = ("brevMap.put(\""+brevkode+"\", () ->\n\tnew Doksysbrev(\""+brevkodeInBrevsystem+"\",\n\t\t"+
     booleanConvert(nullCheck("", redigerbart))+",\n\t\t"+
     nullCheck("", "\""+dekode+"\"")+",\n\t\t"+
     nullCheck("BrevkategoriCode.", brevkategori)+",\n\t\t"+
     nullCheck("DokumenttypeCode.", dokumenttype)+",\n\t\t"+
     getSprakForBrev(brevkodeInBrevsystem)+",\n\t\t"+
     booleanConvert(nullCheck("",visIPselv))+",\n\t\t"+
     nullCheck("BrevUtlandCode.", utland)+",\n\t\t"+
     nullCheck("BrevregeltypeCode.", regeltype)+",\n\t\t"+
     nullCheck("BrevkravtypeCode.", kravtype)+",\n\t\t"+
     nullCheck("DokumentkategoriCode.", dokumentkategori)+",\n\t\t"+
     booleanConvert(nullCheck("",synligForVeileder))+",\n\t\t"+
     nullCheck("BrevkontekstCode.", brevkontekst)+",\n\t\t"+
     nullCheck("", prioritet)+",\n\t\t"+
     nullCheck("", "\""+dokumentmalId+"\"")+",\n\t\t"+
     nullCheck("", "\""+felleselementId+"\"")+",\n\t\t"+
     getVedleggForBrev(brevkode)+"));")
     return putBrev

def appendBatchbrev(filename):
    counterGammelBatchbrev = 0
    counterDoksysBatchbrev = 0
    with open('inputfiler/batchbrevWithXsd.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        for row in readCSV:
            if(row[18]=="1"):
                if(row[14]=="0" or row[14]==""):
                    counterGammelBatchbrev+=1
                    f = open(filename, "a")
                    f.write(str(createGammeltBrev(row[0],row[1], row[2], row[6], row[8], row[3], row[9], row[13], row[7], row[12], row[4], row[11], row[10], row[19], row[5])))
                    f.write("\n")
                    f.close()
                elif(row[14]=="1"):
                    counterDoksysBatchbrev+=1
                    f = open(filename, "a")
                    f.write(str(createDoksysBrev(row[0],row[1], row[2], row[6], row[8], row[3], row[9], row[13], row[7], row[12], row[4], row[11], row[10],row[19], row[15], "00001")))
                    f.write("\n")
                    f.close()
    print("Finished writing "+str(counterGammelBatchbrev)+" batchbrev of type GammelBrev and "+str(counterDoksysBatchbrev)+" batchbrevbrev of type DoksysBrev to file " + filename)


def writeBrevMapper():
    filename = "generatedBrevdataMapper.txt"
    counterGammel = 0
    counterDoksys = 0
    f = open(filename, "w")
    f.write("")
    f.close()
    with open('inputfiler/brevdataWithXsd.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        for row in readCSV:
            if(row[9]=="1"):
                if(row[23]=="0" or row[23]==""):
                    counterGammel+=1
                    f = open(filename, "a")
                    f.write(str(createGammeltBrev(row[0],row[0], row[5], row[6], row[15], row[2], row[16], row[20], row[14], row[19], row[3], row[18], row[17], row[21], row[4])))
                    f.write("\n")
                    f.close()
                elif(row[23]=="1"):
                    counterDoksys+=1
                    f = open(filename, "a")
                    f.write(str(createDoksysBrev(row[0],row[0], row[5], row[6], row[15], row[2], row[16], row[20], row[14], row[19], row[3], row[18], row[17], row[21], row[22], "00001")))
                    f.write("\n")
                    f.close()
    print("Finished writing "+str(counterGammel)+" brev of type GammelBrev and "+str(counterDoksys)+" brev of type DoksysBrev to file " + filename)
    appendBatchbrev(filename)


def createBrevXsdFiles():
    with open('inputfiler/brevdataWithXsd.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        directory = "generatedXsdFiles"
        xsdCounter = 0
        for row in readCSV:
            if(row[23]=="1" and row[9]=="1"):
                xsdCounter+=1
                f = open(directory+"/"+row[22]+".xsd", "w")
                f.write(str(row[25]))
                f.close()
        print("Generated "+str(xsdCounter)+" xsd-files in directory "+directory)

def writeDoksysVedleggMapper():
    filename = "generatedDoksysVedleggMapper.txt"
    counterVedlegg = 0
    f = open(filename, "w")
    f.write("")
    f.close()
    with open('inputfiler/vedleggWithXsd.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        for row in readCSV:
            if(row[5]=="1"):
                counterVedlegg+=1
                f = open(filename, "a")
                f.write(str(createDoksysVedlegg(row[0], row[1], row[10]))+"\n")
                f.close()
    print("Finished writing "+str(counterVedlegg)+" vedlegg to file " + filename)
            

def createDoksysVedlegg(vedleggkode, dekode, dokumentmalId):
    putVedlegg = ("vedleggMap.put(\""+vedleggkode+"\", () -> \n\tnew DoksysVedlegg(\n\t\t"
                "\""+vedleggkode+"\",\n\t\t"
                "\""+dekode+"\",\n\t\t"
                "\""+dokumentmalId+"\",\n\t\t"
                "\"00001\"));")
    return putVedlegg
           

def createVedleggXsdFiles():
    with open('inputfiler/vedleggWithXsd.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        directory = "generatedXsdFiles"
        xsdCounter = 0
        for row in readCSV:
            if(row[5]=="1"):
                xsdCounter+=1
                f = open(directory+"/"+row[2]+".xsd", "w")
                f.write(row[11])
                f.close()
        print("Generated "+str(xsdCounter)+" vedlegg xsd-files in directory "+directory)

def writeSakBrevMapper():
    filename = "generatedSakBrevMapper.txt"
    sakmap=""
    sakdict = {}
    with open('inputfiler/brevForSakMappings.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=';')
        for row in readCSV:
            if(row[3]=="1"):
                if(row[1] in sakdict):
                    sakdict[row[1]].append(row[0])
                else:
                    sakdict[row[1]]=[row[0]]
    for key in sakdict:
        keyCounter=0
        sakmap+="sakToBrevMap.put(\""+key+"\", Arrays.asList("
        for kode in sakdict[key]:
            keyCounter+=1
            sakmap+="\""+kode+"\", "
        sakmap=sakmap[:-2]+"));\n"
        print("Generated "+str(keyCounter)+" bevkode mappings for saktype "+key)
        
    f = open(filename, "w")
    f.write(sakmap)
    f.close()
    
def createBatchBrev():
    print("TODO")



def main():
    print("GENERATING BREVDATAMAPPER:")
    writeBrevMapper()
    print
    print("GENERATING DOKSYSVEDLEGGMAPPER:")
    writeDoksysVedleggMapper()
    print
    print("GENERATE XSD-FILES")
    createBrevXsdFiles()
    createVedleggXsdFiles()
    print
    print("GENERATE SAKBREVMAPPER")
    writeSakBrevMapper()


    
main()
'''
Rekkefoelge returnert fra spoerring mot T_K_BATCHBREV
0K_BATCHBREV_ID, 
1K_BREV_T,
2REDIGERBART,
3K_DOK_T, 
4K_DOK_KATEGORI, 
5BREVGRUPPE, 
6dekode, 
7K_BREV_REGEL_T,
8K_BREV_KATEGORI_T, 
9K_BREV_UTLAND_T, 
10K_BREV_KONTEKST_T, 
11SYNLIG_FOR_VEILEDER, 
12K_BREV_KRAV_T, 
13VIS_I_PSELV,
14DOKSYS,
15DOKUMENT_TYPE_ID,
16DOKUMENTMAL,
17DOKUMENTMAL_FELLESELEMENT
18ER_GYLDIG
19PRIORITET
'''

'''
Rekkefoelge returnert fra spoerring mot T_K_BREV_T
0K_BREV_T
1K_FAGOMRADE
2K_DOK_T
3K_DOK_KATEGORI
4BREVGRUPPE
5REDIGERBART
6DEKODE
7DATO_FOM
8DATO_TOM
9ER_GYLDIG
10DATO_OPPRETTET
11OPPRETTET_AV
12DATO_ENDRET
13ENDRET_AV
14K_BREV_REGEL_T
15K_BREV_KATEGORI_T
16K_BREV_UTLAND_T
17K_BREV_KONTEKST_T
18SYNLIG_FOR_VEILEDER
19K_BREV_KRAV_T
20VIS_I_PSELV
21PRIORITET
22DOKUMENT_TYPE_ID
23DOKSYS
24DOKUMENT_TYPE_ID_1
25DOKUMENTMAL
26DOKUMENTMAL_FELLESELEMENT
27DOKUMENTMAL_REFERANSE
28DATO_OPPRETTET_1
29OPPRETTET_AV_1
30DATO_ENDRET_1
31ENDRET_AV_1
32VERSJON
'''
