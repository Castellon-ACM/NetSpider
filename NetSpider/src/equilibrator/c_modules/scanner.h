#ifndef SCANNER_H
#define SCANNER_H

#include <unordered_map>
#include <string>
#include "ports.h"

// declaro
class PortScanner {
public:
    
    PortScanner(const std::string& ipAddress) : ip(ipAddress), portServiceMap(PORT_SERVICE_MAP) {
    operating_system = getOperatingSystem();

}

    void scan() {
        std::vector<std::thread> threads;
        for (const auto& port : PORT_SERVICE_MAP) {
            threads.emplace_back(&PortScanner::scanPort, this, port.first);
        }

        for (auto& t : threads) {
            t.join();
        }
    }

    std::unordered_map<int, std::string> getOpenPorts() const {
        return open_ports;
        
    }

    int getClosedPorts() const {
        return closedPorts;
    }

    std::string getOperatingSystemX() const {
        return operating_system;
    }



private:
    // Atributos
    std::string ip;
    const std::unordered_map<int, std::string>& portServiceMap;
    std::unordered_map<int, std::string> open_ports;
    std::string operating_system;
    std::mutex port_mutex;
    int closedPorts = 0;

    std::string getOperatingSystem(){
        char buffer[128];
        std::string result = "";
        FILE* pipe = popen(("ping -c 1 " + ip).c_str(), "r"); 
        // aqui le paso a file que es un puntero a la estructura de file,
        // el puntero apunta a la direccion de memoria del sistema operativo que contiene como manejar los archivos, el puntero guarda lo del fopen
        // popen abre un pipe, que es una tuberia que se usa para comunicar procesos, en este caso se usa para comunicar el proceso de ping con el
        // proceso de mi programa, bloste
        if (!pipe) return "ERROR";

           while (!feof(pipe)) {
            if (fgets(buffer, 128, pipe) != NULL)
                result += buffer;
        }

        pclose(pipe);

        // convierto el bloste a minusculas, el otro begin dice donde meter el bloste

        std::transform(result.begin(), result.end(), result.begin(), ::tolower);

        size_t pos = result.find("ttl="); 
        
        // si encuentra algo, devuelve pos, 
        // que es size_t. npos es un valor que se usa para indicar que no se encontro nada
        // osea que si result find devuelve npos, no se encontro ttl, es como false.
        if (pos != std::string::npos) {
            int ttl = std::stoi(result.substr(pos + 4));
            if (ttl <= 70) return "Linux";
            else return "Windows";
        }
        return "Undetermined";
    }

    void scanPort(int port){
        int sockfd = socket(AF_INET, SOCK_STREAM, 0);
        if (sockfd == -1) return; // If it gives an error, it fails and returns.

        sockaddr_in server; // Guarda info del server
        server.sin_family = AF_INET; // af inet (IPV4 BLOSTE)
        server.sin_port = htons(port); //htons (NUMERO DE BLOSTES DEL PUERTO)
        inet_pton(AF_INET, ip.c_str(), &server.sin_addr); 
        // Converts the ip to
        // a binary so it can be saved in the sin address.

        // en el inet, se mete el protocolo ipv4, luego se pasa a string la ip,
        // luego de eso, le digo que quiero guardar la direccion ip en binario de mi
        // servidor en el sin address pointer, apuntando al server, ahi es donde la funcion
        // tocara el bloste.

        // Set timeout
        timeval timeout;
        timeout.tv_sec = 0;
        timeout.tv_usec = 100000; 
        
        // esto le dice el timeout a mi socket, en sus opciones
        setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, &timeout, sizeof(timeout));
        // guarda el resultado. si va la conexion, es 1, si no, 0.
        int result = connect(sockfd, (struct sockaddr *)&server, sizeof(server));
        close(sockfd);

        if (result == 0) {
            // bloquea la edicion del puerto mutex, asegura que sea 
            // desbloqueado al terminar la edicion


            // el mutex hace que solo lo debajo de el se bloquee, hasta 
            // que salga del scope, osea, ya terminado
            std::lock_guard<std::mutex> lock(port_mutex);

            //findea en el map el puerto que he escaneado

            auto service = PORT_SERVICE_MAP.find(port);

            // port service map es basicamente dentro del iterador,
            // lo que significa que ha llegado al final. si es end, es que
            // no lo ha encontrado, si no es end, es que ha salido guay
            // y le dice que coja el segundo valor que es la valor asociado a la key
            if (service != PORT_SERVICE_MAP.end()) {
                open_ports[port] = service->second;
            } else {
                open_ports[port] = "Unknown";
            }
        } else {
            closedPorts++;
        }

    }

    





};

#endif