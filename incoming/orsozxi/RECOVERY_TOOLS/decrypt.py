from pathlib import Path
import xml.etree.ElementTree as ET,hashlib,base64,re,json,csv,collections
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad,pad
import xlrd
from sqlcipher3 import dbapi2 as sql
ROOT=Path(__file__).resolve().parents[1]; A=ROOT/'assets'; O=ROOT/'DECRYPTED_CONTENT'; O.mkdir(exist_ok=True)
S={e.attrib['name']:e.text or '' for e in ET.parse(ROOT/'RECOVERED_CODE/resources/res/values/strings.xml').getroot() if e.tag=='string'}
pw=''.join(S[k] for k in 'none cfive ntwo csix nthree cone nfour cfour nfive cone nsix'.split());iv=b'\0'*12+b'orso';key=hashlib.pbkdf2_hmac('sha1',pw.encode(),iv,100,32)
def dec(b):
 plain=unpad(AES.new(key,AES.MODE_CBC,iv).decrypt(b),16)
 assert AES.new(key,AES.MODE_CBC,iv).encrypt(pad(plain,16))==b
 return plain.decode('utf-8')
report={'orso':[],'workbooks':[],'errors':[],'encrypted_cells':0,'sheets':0,'cell_positions':0}
for f in sorted(A.rglob('*.orso')):
 try:
  s=dec(f.read_bytes()); out=O/'documents'/f.relative_to(A).with_suffix('.txt');out.parent.mkdir(parents=True,exist_ok=True);out.write_text(s,encoding='utf-8');report['orso'].append({'source':str(f.relative_to(ROOT)),'output':str(out.relative_to(ROOT)),'sha256':hashlib.sha256(out.read_bytes()).hexdigest()})
 except Exception as e:report['errors'].append({'path':str(f),'error':str(e)})
print('OR SO complete',len(report['orso']),flush=True)
for f in sorted(A.rglob('*.xls')):
 try:
  b=xlrd.open_workbook(str(f)); wr={'source':str(f.relative_to(ROOT)),'sheets':[],'encrypted_cells':0}
  for n,sheet in enumerate(b.sheets()):
   rows=[];enc=0
   for r in range(sheet.nrows):
    row=[]
    for c in range(sheet.ncols):
     v=sheet.cell_value(r,c)
     if isinstance(v,str):
      try: raw=base64.b64decode(re.sub(r'\s+','',v),validate=True)
      except Exception:raw=b''
      if raw and len(raw)%32==0 and re.fullmatch(b'[0-9A-Fa-f]+',raw):v=dec(bytes.fromhex(raw.decode()));enc+=1
     row.append(v)
    rows.append(row)
   out=O/'spreadsheets'/f.relative_to(A).with_suffix('')/f'{n+1:02d}.csv';out.parent.mkdir(parents=True,exist_ok=True)
   with out.open('w',encoding='utf-8-sig',newline='') as w:csv.writer(w).writerows(rows)
   wr['sheets'].append({'name':sheet.name,'rows':sheet.nrows,'columns':sheet.ncols,'output':str(out.relative_to(ROOT))})
   wr['encrypted_cells']+=enc;report['sheets']+=1;report['cell_positions']+=sheet.nrows*sheet.ncols
  report['workbooks'].append(wr);report['encrypted_cells']+=wr['encrypted_cells']
 except Exception as e:report['errors'].append({'path':str(f),'error':str(e)})
print('XLS complete',len(report['workbooks']),report['encrypted_cells'],flush=True)
dbpw=''.join(S[k] for k in 'mm cone none bb cfour ntwo mm cone nthree csix nfour cfive nfive'.split())
for compat in [4,3,2,1]:
 db=sql.connect(str(A/'mybible/Bible'))
 db.execute('PRAGMA key="'+dbpw.replace('"','""')+'"');db.execute('PRAGMA cipher_compatibility='+str(compat))
 try:tables=db.execute("select name from sqlite_master where type='table'").fetchall()
 except Exception:db.close();continue
 dest=O/'bible';dest.mkdir(exist_ok=True);plain=dest/'Bible.sqlite'
 db.execute("ATTACH DATABASE ? AS plaintext KEY ''",(str(plain),));db.execute("SELECT sqlcipher_export('plaintext')");db.execute('DETACH DATABASE plaintext')
 report['database']={'compatibility':compat,'integrity_check':db.execute('PRAGMA integrity_check').fetchall(),'tables':{}}
 for (table,) in tables:
  cur=db.execute('SELECT * FROM "'+table.replace('"','""')+'"');rows=cur.fetchall();report['database']['tables'][table]=len(rows)
  with (dest/(table+'.csv')).open('w',encoding='utf-8-sig',newline='') as w:
   writer=csv.writer(w);writer.writerow([x[0] for x in cur.description]);writer.writerows(rows)
 db.close();break
else:report['errors'].append({'path':'assets/mybible/Bible','error':'No compatible database configuration succeeded'})
(O/'verification.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
print(json.dumps({k:v for k,v in report.items() if k not in ['orso','workbooks']},ensure_ascii=False),flush=True)
