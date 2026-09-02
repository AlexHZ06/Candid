
import { useNavigate } from "react-router-dom"

export class jwtService{

    navigation = useNavigate()

    checkTokenPhotographer(){

        return fetch("/api/jwt/public/checkjwtphotographer", {

            method:"POST",

            headers:{

                auth:localStorage.getItem("jwt")

            }

        }).then(response => response.json()).then(data =>{
            
            if(data === true){

                return

            }
            else{

                fetch("/api/jwt/public/requestjwt",{

                    method:"POST",
                    headers:{

                        auth:localStorage.getItem("jwt"),
                        refreshTokenUUID:localStorage.getItem("refreshUUID")                   

                    }

                }).then(res => res.json()).then(dat =>{

                    if(dat.sucsess === true){

                        localStorage.setItem("jwt", dat.data)
                        return

                    }
                    else{

                        localStorage.clear()
                        this.navigation("/rejectedjwt")

                    }

                })

            }

        })

    }

    requestJwt(){

        return fetch("/jwt/public/requestjwt", {

            method:"POST",
            headers:{

                auth:localStorage.getItem("jwt"),
                refreshTokenUUID:localStorage.getItem("refreshUUID")                   

            }

        }).then(response => response.json()).then(data => {

            if(!data.sucsess){

                localStorage.clear()
                this.navigation("/rejectedjwt")
                return false
            }
            else{

                localStorage.setItem("jwt", data.data)
                return true

            }
            

        })

    }

    checkTokenClient(){

        return fetch("/api/jwt/public/checkjwtclient", {

            method:"POST",

            headers:{

                auth:localStorage.getItem("jwt")

            }

        }).then(response => response.json()).then(data =>{
            
            if(data === true){

                return

            }
            else{

                fetch("/api/jwt/public/requestjwt",{

                    method:"POST",
                    headers:{

                        auth:localStorage.getItem("jwt"),
                        refreshTokenUUID:localStorage.getItem("refreshUUID")                   

                    }

                }).then(res => res.json()).then(dat =>{

                    if(dat.sucsess === true){

                        localStorage.setItem("jwt", dat.data)
                        return

                    }
                    else{

                        localStorage.clear()
                        this.navigation("/rejectedjwt")

                    }

                })

            }

        })

    }

}

