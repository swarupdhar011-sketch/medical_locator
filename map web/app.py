from flask import Flask, render_template, jsonify
import sqlite3
import os

app = Flask(__name__)

def get_db_connection():
    db_path = os.path.join(os.path.dirname(__file__), 'hospitals.db')
    conn = sqlite3.connect(db_path)
    conn.row_factory = sqlite3.Row
    return conn

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/api/hospitals')
def get_hospitals():
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT name, type, latitude, longitude, address, phone, facilities, reviews FROM hospitals")
        hospitals = [dict(row) for row in cursor.fetchall()]
        conn.close()
        return jsonify(hospitals)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    print("Launching Local Server on HTTP (No SSL)...")
    app.run(host='127.0.0.1', port=5000, debug=True)