import socket
import threading
import subprocess
import ports
import platform
# ME DAN IP. COMO SEGUNDO PARAMETRO QUIET O VERBOSE, CON QUIET DEVUELVE COSAS CON VERBOSE MAS COSAS.
# PUERTOS ABIERTOS TCP, VERSION (80 ES HTTP), TECNOLOGIA, SISTEMA OPERATIVO.



class Scanner:
    def __init__(self, ip, verbose=False):

        
        self.ip = ip
        self.verbose = verbose
        self.open_ports = {}
        self.closed_ports = []
        

        self.operating_system = self.getOperatingSystem()


    def scan_port(self, port):
        try:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.settimeout(0.1)
            
            s.connect((self.ip, port))   
            
            s.close()

        

            self.open_ports[port] = ports.PORT_SERVICE_MAP[port]
            

        except:

            self.closed_ports.append(port)

    def scan_ports(self):

        threads = []
        for port in ports.PORT_SERVICE_MAP:


            t = threading.Thread(target=self.scan_port, args=(port,))
            threads.append(t)
            t.start()


        for t in threads:
            t.join()

    def getOperatingSystem(self):
        ttlNumber = 0
        if platform.system().lower() == "windows":
            command = f"ping -n 1 {self.ip}"
        else:
            command = f"ping -c 1 {self.ip}"
    
        try:
            result = subprocess.check_output(command, shell=True, text=True)
        
        # Buscar la primera línea que contiene TTL

            for line in result.splitlines():
                if "TTL=" in line.upper():
                    ttlNumber = int(line.upper().split("TTL=")[1].split()[0])

                    print(ttlNumber)

                    if ttlNumber <= 70:
                        return "Linux"
                    else:
                        return "Windows"
                
            return "Undetermined"
        
        except subprocess.CalledProcessError:
            return "NotReachable"
        except Exception as e:
            return f"Error: {str(e)}"

        

    def scan(self):
        self.scan_ports()
        if self.verbose:
            print(f"Open ports: {self.open_ports}")
            print(f"Operating System: {self.operating_system}")

            ## print(f"Closed ports: {self.closed_ports}")

        else:
            print(f"Open ports: {self.open_ports}")

    def get_open_ports(self):
        return self.open_ports

    def get_closed_ports(self):
        return self.closed_ports

    def get_ip(self):
        return self.ip

    def get_verbose(self):
        return self.verbose

    def set_ip(self, ip):
        self.ip = ip

    def set_verbose(self, verbose):
        self.verbose = verbose


def main():

    ip = "192.168.1.151"
    scanner = Scanner(ip, verbose=True)
    scanner.scan()
   
main()
     