
import json
from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    if (request.method == 'POST'):
        json_e = request.get_json()
        e = json.loads(json_e)
        json_i = request.get_json()
        i = json.loads(json_i)

        c = 2

        if c == 1 or c == 2:
            orden_valor = True
            orden_fecha = False
        elif c == 3 or c == 4:
            orden_valor = False
            orden_fecha = True

        if c == 1 or c == 3:
            primero_egreso = True
            primero_ingreso = False
        elif c == 2 or c == 4:
            primero_egreso = False
            primero_ingreso = True


        if orden_valor:
            e2 = ({key: value for key, value in sorted(e.items(), key=lambda item: item[1]['valor'])})
            i2 = ({key: value for key, value in sorted(i.items(), key=lambda item: item[1]['valor'])})

        if orden_fecha:
            e2 = ({key: value for key, value in sorted(e.items(), key=lambda item: item[1]['fecha'])})
            i2= ({key: value for key, value in sorted(i.items(), key=lambda item: item[1]['fecha'])})

        if primero_egreso:
            for x in e2:
                for y in i2:
                    if e2[x]['valor'] <= i2[y]['valor']:
                        print(e2[x]['id'] + i2[y]['id']) 
                        break

        if primero_ingreso:
            for x in i2:
                for y in e2:
                    if e2[y]['valor'] <= i2[x]['valor']:
                        print(e2[y]['id'] + i2[x]['id']) 
                        break

if __name__ == '__main__':
    app.run()