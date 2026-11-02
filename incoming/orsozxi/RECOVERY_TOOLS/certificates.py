import struct,base64,json
from pathlib import Path
R=Path(__file__).resolve().parents[1];O=R/'DECRYPTED_CONTENT/certificates';O.mkdir(exist_ok=True);results=[]
for f in (R/'assets').rglob('*.bks'):
 b=f.read_bytes();pos=0
 def num(fmt):
  global pos
  v=struct.unpack_from(fmt,b,pos)[0];pos+=struct.calcsize(fmt);return v
 def take(n):
  global pos
  x=b[pos:pos+n];pos+=n;return x
 def utf():return take(num('>H')).decode('utf-8')
 def cert():return utf(),take(num('>I'))
 version=num('>I');salt=take(num('>I'));iterations=num('>I');entries=[]
 while True:
  t=num('>B')
  if t==0:break
  alias=utf();timestamp=num('>Q');chain=[cert() for _ in range(num('>I'))]
  assert t==1,('unexpected non-certificate entry',f,t)
  typ,data=cert();out=O/f.stem/(str(len(entries)+1).zfill(2)+'.pem');out.parent.mkdir(exist_ok=True)
  encoded=base64.b64encode(data).decode();out.write_text('-----BEGIN CERTIFICATE-----\n'+'\n'.join(encoded[i:i+64] for i in range(0,len(encoded),64))+'\n-----END CERTIFICATE-----\n')
  entries.append({'alias':alias,'type':typ,'output':str(out.relative_to(R))})
 results.append({'source':str(f.relative_to(R)),'version':version,'entries':entries,'note':'Public certificates stored in plaintext; no encrypted key entries. Store MAC not verified.'})
(O/'index.json').write_text(json.dumps(results,ensure_ascii=False,indent=2));print([(r['source'],len(r['entries'])) for r in results])
