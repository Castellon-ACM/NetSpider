#ifndef SCANNER_H
#define SCANNER_H
#include <algorithm>
#include <unordered_map>
#include <string>
#include "ports.h"

// declaro
class PortScanner {
public:
    
    PortScanner(const std::string& ipAddress) : ip(ipAddress), portServiceMap(PORT_SERVICE_MAP), io_context(), resolver(io_context) {
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

    boost::asio::io_context io_context;
    boost::asio::ip::tcp::resolver resolver;

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

     try {
            boost::asio::ip::tcp::socket socket(io_context);
            boost::asio::ip::tcp::endpoint endpoint(
                boost::asio::ip::address::from_string(ip), port
            );

            boost::system::error_code error;
            socket.connect(endpoint, error);

     
   


        if (!error) {
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

        } catch (std::exception& e) {
            ++closedPorts;
        
        } 
        
    }



    





};

#endif