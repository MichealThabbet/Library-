import gsap from 'gsap';
import * as THREE from 'three';
import { chromium } from 'playwright';
import { writeFileSync } from 'node:fs';

const results = {};
function assert(value, message) { if (!value) throw new Error(message); }

const obj = { x: 0 };
gsap.set(obj, { x: 100 });
assert(obj.x === 100, 'GSAP basic animation/set failed');
results.gsap = { version: gsap.version, passed: true };

const geometry = new THREE.BoxGeometry(1, 1, 1);
assert(geometry.getAttribute('position').count === 24, 'Three.js geometry failed');
assert(new THREE.Vector3(1, 2, 3).multiplyScalar(2).equals(new THREE.Vector3(2, 4, 6)), 'Three.js math failed');
results.three = { revision: THREE.REVISION, passed: true };

const browser = await chromium.launch({ headless: true, args: [
  '--no-sandbox', '--enable-webgl', '--enable-unsafe-swiftshader',
  '--use-gl=angle', '--use-angle=swiftshader'
] });
let hasError = false;
try {
  for (const [width, height] of [[390,844],[768,1024],[1440,900]]) {
    const page = await browser.newPage({ viewport: { width, height } });
    try {
      await page.setContent(`<!doctype html><html><head><meta name="viewport" content="width=device-width,initial-scale=1">
      <style>body{margin:0;background:#322116;color:#e7c576}
      main{width:min(90%,920px);margin:auto;min-height:90vh}
      h1{font-size:clamp(28px,5vw,70px)}#animated{width:40px;height:40px;background:#e7c576}
      </style></head><body><main><h1>Monastery visual test</h1><div id="animated"></div></main></body></html>`);
      await page.addScriptTag({path:'node_modules/gsap/dist/gsap.min.js'});
      const animation = await page.evaluate(async () => {
        const element = document.getElementById('animated');
        await window.gsap.to(element, { x:80, duration:0.05 }).then();
        return getComputedStyle(element).transform !== 'none';
      });
      const overflow = await page.evaluate(() => document.documentElement.scrollWidth > innerWidth);
      const webgl = await page.evaluate(() => {
        const gl = document.createElement('canvas').getContext('webgl2') ||
                   document.createElement('canvas').getContext('webgl');
        if (!gl) return false;
        const shader = gl.createShader(gl.VERTEX_SHADER);
        gl.shaderSource(shader, 'attribute vec4 position; void main() { gl_Position = position; }');
        gl.compileShader(shader);
        return gl.getShaderParameter(shader, gl.COMPILE_STATUS) === true;
      });
      await page.screenshot({ path: `browser_${width}.png`, fullPage: true });
      results[`browser_${width}`] = { animation, no_overflow: !overflow, webgl, passed: animation && !overflow && webgl };
      if (!results[`browser_${width}`].passed) hasError = true;
    } finally { await page.close(); }
  }
} finally { await browser.close(); }
writeFileSync('js-test-results.json', JSON.stringify(results,null,2));
console.log(JSON.stringify(results,null,2));
assert(!hasError, 'One or more browser test cases failed');
