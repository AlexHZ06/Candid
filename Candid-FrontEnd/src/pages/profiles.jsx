import CHeader from "../compnents/cheader"
import { Link } from "react-router-dom"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt";

function Profiles(){

    const tokenService = new jwtService()
    const [profiles, setProfiles] = useState();

    useEffect(() =>{

        fetch("/api/profile/client/getprofiles",{

            method:"GET",
            headers:{

                "Content-Type":"application/json",
                auth:localStorage.getItem("jwt")

            }

        }).then(response => response.json()).then(async data => {

            if(data.success){

                setProfiles(data.data)
                console.log(data.data)

            }
            else{

                if(data.internalCode === 401){

                    let result = await tokenService.requestJwt()
                    if(result){

                        fetch("/api/profile/client/getprofiles",{

                            method:"GET",
                            headers:{

                                "Content-Type":"application/json",
                                auth:localStorage.getItem("jwt")

                            }

                        }).then(res => res.json()).then(d => {

                            if(data.success){

                                setProfiles(d.data)
                                console.log(d.data)

                            }

                        })                        

                    }

                }

            }

        })

    }, [])

    return(

        <div className="
        
            w-screen 
            min-h-screen
        
        ">
            <CHeader active={"Search"}/>
            <div className="

                flex
                felx-col
                items-center
                justify-center

            ">
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-8/10
                    h-[80vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                
                ">
                    <div className="
                    
                        w-9/10
                        flex
                        mt-5
                        justify-center
                        items-center
                        xl:mt-2
                        xl:mb-2
                    
                    ">
                        <p className="
                        
                            font-semibold
                            tracking-wide
                            text-neutral-700
                            text-2xl
                        
                            
                        
                        ">Profiles</p>
                    </div>
                        <div className="
                    
                        border
                        border-neutral-300
                        rounded-xl
                        w-9/10
                        h-[80%]
                        pt-5
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                        flex
                        flex-row
                        
                        gap-4
                        overflow-y-auto
              
                        justify-center
                        shrink-0
                        flex-wrap
                        
                        
                        content-start
           
                    
                    ">
                        
                        {profiles?.map(profile => (

                            <Link className="
                            
                                w-[20vh]
                                bg-neutral-50
                                h-[20vh]
                                rounded-xl
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                hover:shadow-[0_0px_10px_rgba(0,0,0,0.35)]
                                active:shadow-[0_0px_5px_rgba(0,0,0,0.15)_inset]
                                flex
                                justify-center
                                
                            "
                            >
                                <p className="
                                
                                    mt-10
                                    

                                ">{profile.profilename}</p>
                            </Link>

                        ))}

                    </div>
                    <div className="
                    
                        w-10/10
                        flex
                        flex-row
                        items-center
                        justify-center
                        mt-5

                        xl:mt-2
                    ">

                        <Link to={"/createprofile"} className="
                        
                            bg-black
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-gray-900
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                            xl:h-[5vh]
                            xl:w-[15vh]
                        
                        ">Add Profile</Link>

                    </div>

                </div>
            </div>
        </div>

    )

}

export default Profiles