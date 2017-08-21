package utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import modelo.DaoMaster;

/**
 * Created by martin on 20/08/2017.
 */

public class DatabaseUtilities extends DaoMaster{


    public DatabaseUtilities(SQLiteDatabase db) {
        super(db);
    }

    public static class DevOpenHelper extends DaoMaster.DevOpenHelper{


        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            super.onCreate(db);
            //Inserto novedades
            db.execSQL("INSERT INTO novedad VALUES (1,1,'Medidor OK',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (2,2,'Medidor roto No Leido',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (3,3,'Cortado sin medidor',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (4,4,'Cambio medidor',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (5,5,'R.O.B.O.(req.op.bajo.obs)',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (6,6,'Medidor sin tapa o rota',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (7,7,'Medidor sin acceso',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (8,8,'Medidor en cero',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (9,9,'Alto consumo',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (10,10,'Encontrado - Cambiar orden',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (11,11,'Corregir digitos',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (12,12,'Med.c-obstaculo superable',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (13,13,'Tapa medidor trabada',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (14,14,'Nro de medidor borroso',0,'A');");
            db.execSQL("INSERT INTO novedad VALUES (15,15,'Lectura con dificultad',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (16,16,'Profundidad mayor a 30 cm',0,'A');");
            db.execSQL("INSERT INTO novedad VALUES (17,17,'Medidor empañado',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (18,18,'Camara inundada',0,'A');");
            db.execSQL("INSERT INTO novedad VALUES (19,19,'Perdidas en conexión',0,'A');");
            db.execSQL("INSERT INTO novedad VALUES (20,20,'Perdidas y Cámara inundada',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (21,21,'Medidor profundo e inund.',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (22,22,'Medidor profundo y empañado',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (23,23,'Pérdidas y med. empañado',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (24,24,'Instalación Peligrosa',0,'E');");
            db.execSQL("INSERT INTO novedad VALUES (25,25,'Medidor trabado',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (26,26,'No medido por fuerza mayor',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (27,27,'Conexión directa',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (28,28,'Medidor roto per leío',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (29,29,'Med.con obstaculo insuperable',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (30,30,'Medidor sin cámara',0	,'A');");
            db.execSQL("INSERT INTO novedad VALUES (31,31,'Cámara tapada con tierra',1,'A');");
            db.execSQL("INSERT INTO novedad VALUES (32,32,'Visor opaco legible con dif.',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (33,33,'Visor opaco No legible',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (34,34,'Acceso Peligroso',0,'E');");
            db.execSQL("INSERT INTO novedad VALUES (35,35,'Acceso Dificil Med.Leido',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (36,36,'Cámara Inestable Med.Leido',0,'A');");
            db.execSQL("INSERT INTO novedad VALUES (37,37,'Med.Elct.c-Display Apagado',1,'E');");
            db.execSQL("INSERT INTO novedad VALUES (55,55,'Medidor Inexistente',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (90,90,'ACTIVIDAD NO RESIDENCIAL',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (92,92,'TERRENO BALDIO',1,'T');");
            db.execSQL("INSERT INTO novedad VALUES (98,98,'SALIR',0,'T');");
            db.execSQL("INSERT INTO novedad VALUES (99,99,'PROXIMO MEDIDOR',0,'T');");
            //Inserto tipos de medidores
            db.execSQL("INSERT INTO tipo_medidor VALUES (1,0,'Medidor de agua');");
            db.execSQL("INSERT INTO tipo_medidor VALUES (2,1,'Medidor de energía activa');");
            db.execSQL("INSERT INTO tipo_medidor VALUES (3,2,'Medidor de energía reactiva');");
            //Inserto zonas
            db.execSQL("INSERT INTO zona VALUES (1,'DON BOSCO',2);");
            db.execSQL("INSERT INTO zona VALUES (2,'RIVADAVIA',2);");
            db.execSQL("INSERT INTO zona VALUES (3,'CARCEL',2);");
            db.execSQL("INSERT INTO zona VALUES (4,'CENTRAL',2);");
            db.execSQL("INSERT INTO zona VALUES (5,'SANDRA',2);");
            db.execSQL("INSERT INTO zona VALUES (6,'BELGRANO',2);");
            db.execSQL("INSERT INTO zona VALUES (7,'PELEGRINI',2);");
            db.execSQL("INSERT INTO zona VALUES (8,'LEWIS JONES',2);");
            db.execSQL("INSERT INTO zona VALUES (9,'Bo.RIO CHUBUT',2);");
            db.execSQL("INSERT INTO zona VALUES (10,'Bo.MALVINAS',2);");
            db.execSQL("INSERT INTO zona VALUES (11,'AREA 16',2);");
            db.execSQL("INSERT INTO zona VALUES (12,'Bo.2 DE ABRIL 1',2);");
            db.execSQL("INSERT INTO zona VALUES (13,'Bo.2 DE ABRIL 2',2);");
            db.execSQL("INSERT INTO zona VALUES (14,'Bo.2 DE ABRIL 3',2);");
            db.execSQL("INSERT INTO zona VALUES (15,'Bo.LUIS VERNET',2);");
            db.execSQL("INSERT INTO zona VALUES (16,'VIALIDAD',2);");
            db.execSQL("INSERT INTO zona VALUES (17,'Bo.50 VIVIENDAS',2);");
            db.execSQL("INSERT INTO zona VALUES (18,'LA HERMITA',2);");
            db.execSQL("INSERT INTO zona VALUES (19,'OVEON',2);");
            db.execSQL("INSERT INTO zona VALUES (20,'Bo.SAN PABLO',2);");
            db.execSQL("INSERT INTO zona VALUES (21,'PARQUE G.MAYO 1',2);");
            db.execSQL("INSERT INTO zona VALUES (22,'PARQUE G.MAYO 2',2);");
            db.execSQL("INSERT INTO zona VALUES (23,'Bo.U.P.C.N.',3);");
            db.execSQL("INSERT INTO zona VALUES (24,'CHACRAS MENSUAL',3);");
            db.execSQL("INSERT INTO zona VALUES (25,'PUERTO',3);");
            db.execSQL("INSERT INTO zona VALUES (26,'PLAYA 1',3);");
            db.execSQL("INSERT INTO zona VALUES (27,'PLAYA 2',3);");
            db.execSQL("INSERT INTO zona VALUES (28,'PLAYA 3',3);");
            db.execSQL("INSERT INTO zona VALUES (29,'PLAYA 4',3);");
            db.execSQL("INSERT INTO zona VALUES (30,'CONS. CENTINELA',2);");
            db.execSQL("INSERT INTO zona VALUES (31,'GRAND. USUARIOS',1);");
            db.execSQL("INSERT INTO zona VALUES (32,'GRUPO A SIN MyG',1);");
            db.execSQL("INSERT INTO zona VALUES (33,'TORRE TANQUE',2);");
            db.execSQL("INSERT INTO zona VALUES (37,'Bo.S.E.R.O.S.',2);");
            db.execSQL("INSERT INTO zona VALUES (43,'Bo.3 DE ABRIL',3);");
            db.execSQL("INSERT INTO zona VALUES (50,'Bo.CO-VI-RA',2);");
            db.execSQL("INSERT INTO zona VALUES (51,'Bo.DOCENTE',2);");
            db.execSQL("INSERT INTO zona VALUES (53,'Bo.490 NORTE',2);");
            db.execSQL("INSERT INTO zona VALUES (54,'Bo.490 SUR',2);");
            db.execSQL("INSERT INTO zona VALUES (56,'PLAYA 5',3);");
            db.execSQL("INSERT INTO zona VALUES (57,'Bo.MEDANOS',3);");
            db.execSQL("INSERT INTO zona VALUES (58,'PLAYA MAGAGNA',3);");
            db.execSQL("INSERT INTO zona VALUES (59,'Bo.COMERCIO',2);");
            db.execSQL("INSERT INTO zona VALUES (60,'CHACRAS BIMENS.',3);");
            db.execSQL("INSERT INTO zona VALUES (61,'AREA 18',2);");
            db.execSQL("INSERT INTO zona VALUES (66,'SUBESTACIONES',1);");
            //Inserto grupos
            db.execSQL("INSERT INTO grupo VALUES (1, 'A', 'MENSUAL', 9);");
            db.execSQL("INSERT INTO grupo VALUES (2, 'B', 'RAWSON', 0);");
            db.execSQL("INSERT INTO grupo VALUES (3, 'C', 'PLAYA', 0);");
        }
    }
}


