

import fs from 'fs';
import path from 'path';
import * as templates from './dist/templates.js';
import {NsComponents, WebComponents} from './dist/components.js';

const __dirname = path.dirname(process.argv[1])
const base = path.resolve(__dirname, "dist")

const types = [{
    typ: "native",
    value: NsComponents
}, {
    typ: "web",
    value: WebComponents
}]

for(const [key, value] of Object.entries(templates)){
    for (const obj of types) {
        const data = new value().compile(obj.value);
        console.log(`create vue template for ${key}: ${base}/${data.name}.${obj.typ}.vue`);
        fs.writeFileSync(`${base}/${data.name}.${obj.typ}.vue`, data.content)
    }
}