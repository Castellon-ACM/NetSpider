#include <iostream>
#include <unordered_map>
#include <vector>
#include <thread>
#include <mutex>
#include <chrono>
#include <cstring>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>

#include "scanner.h"
#include "ports.h"



MainScanner::MainScanner()
    : portServiceMap(PORT_SERVICE_MAP) {} // inicio el mapa

void MainScanner::scan(int ip) {
    



}

int main() {
    // Printeo hola
    std::cout << "Hola" << std::endl;
}






