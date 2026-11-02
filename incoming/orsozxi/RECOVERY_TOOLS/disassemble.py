from pathlib import Path
from loguru import logger
logger.remove()
from androguard.core.dex import DEX
import gzip,json
R=Path(__file__).resolve().parents[1];O=R/'RECOVERED_CODE/dex_instructions';O.mkdir(exist_ok=True)
summary=[]
for f in sorted(R.glob('classes*.dex')):
 d=DEX(f.read_bytes());methods=insns=0
 with gzip.open(O/(f.name+'.txt.gz'),'wt',encoding='utf-8') as w:
  for c in d.get_classes():
   w.write('\nCLASS '+c.get_name()+' EXTENDS '+str(c.get_superclassname())+'\n')
   for m in c.get_methods():
    w.write('\nMETHOD '+m.get_name()+m.get_descriptor()+' '+str(m.get_access_flags_string())+'\n')
    if m.get_code():
     methods+=1;offset=0
     for i in m.get_instructions():
      w.write(f'{offset:08x} {i.get_raw().hex()} {i.get_name()} {i.get_output()}\n');offset+=i.get_length();insns+=1
 s={'dex':f.name,'classes':len(d.get_classes()),'methods_with_code':methods,'instructions':insns};summary.append(s);print(s,flush=True)
(O/'index.json').write_text(json.dumps(summary,indent=2))
