
import CHeader from "../compnents/cheader"
import userImg from "../resources/user.png"
import calendar from "../resources/calendar (3).png"
import phone from "../resources/phone-svgrepo-com.svg"
import mail from "../resources/email.png"
import { useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt"
import { Link } from "react-router-dom"

function PhotographerUser(){

    const { userid } = useParams()
    const jwt = new jwtService()
    const [category, setCategory] = useState([])
    const [user, setUser] = useState([])
    const [albums, setAlbums] = useState([])

    useEffect(() => {

        fetch("/api/image/getcatprices", {

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            }, 
            body:JSON.stringify({

                userId:userid

            })          

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/image/getcatprices", {

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            }, 
                            body:JSON.stringify({

                                userId:userid

                            })          

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log(d.data)
                                setCategory(d.data)
                                
                            }
                            else{

                                console.log("error")

                            }

                        })       

                    }

                }
                else{

                    console.log("error")

                }

            }else{

                console.log(data.data)
                setCategory(data.data)

            }

        })

        fetch("/api/auth/getuserbyid", {

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            }, 
            body:JSON.stringify({

                userId:userid

            })          

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/auth/getuserbyid", {

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            }, 
                            body:JSON.stringify({

                                userId:userid

                            })          

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log(d.data)
                                setUser(d.data)
                                
                            }
                            else{

                                console.log("error")

                            }

                        })       

                    }

                }
                else{

                    console.log("error")

                }

            }else{

                console.log(data.data)
                setUser(data.data)

            }

        })

        fetch("/api/album/getallalbums", {

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            }, 
            body:JSON.stringify({

                userId:userid

            })          

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/album/getallalbums", {

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            }, 
                            body:JSON.stringify({

                                userId:userid

                            })          

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log(d.data)
                                setAlbums(d.data)
                                
                            }
                            else{

                                console.log("error")

                            }

                        })       

                    }

                }
                else{

                    console.log("error")

                }

            }else{

                console.log(data.data)
                setAlbums(data.data)

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

                mt-5
                flex-1
                min-h-0
                flex
                flex-col
                overflow-y-scroll
                overflow-x-hidden
                items-center
                w-full
                [&::-webkit-scrollbar]:hidden
                [-ms-overflow-style:none]
                scrollbar-width:none
                
                xl:mt-0

            ">
                <div className="

                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-70
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                    justify-self-center
                    shrink-0

                    xl:h-85

                ">
                    <div className="

                        flex
                        w-full
                        justify-between
                        mt-5

                    ">
                        <div className="

                            flex

                        ">
                            <img className="

                                bg-blue-300
                                rounded-full
                                ml-7
                                w-[10vh]

                            " src={userImg} /> 

                            <div className="

                                ml-5
                                mt-2

                            ">
                                <p className="

                                    font-bold

                                ">{user.firstname} {user.lastname}</p>

                            </div>

                        </div>
                    </div>

                    <div className="

                        w-full
                        h-full
                        flex
                        flex-col
                        p-5

                    ">
                        <div className="

                            w-8/10
                            flex-1
                            flex
                            gap-7
                            p-2

                        ">
                            <div className="

                                flex
                                flex-row
                                gap-5

                            ">
                                <div className="

                                    flex
                                    items-center
                                    gap-2

                                ">
                                    <img className="

                                        w-[2vh]
                                        h-[2vh]

                                    " src={calendar} />                               
                                    <p>joined: {user.datejoined?.split("-").reverse().join("-")}</p>
                                </div>

                                <div className="

                                    flex
                                    items-center
                                    gap-2

                                ">
                                    <img className="

                                        w-[2vh]
                                        h-[2vh]

                                    " src={phone} />

                                    <p>{user.phone}</p>

                                </div>

                                <div className="

                                    flex
                                    items-center
                                    gap-2

                                ">
                                    <img className="

                                        w-[2vh]
                                        h-[2vh]

                                    " src={mail} />

                                    <p>{user.email}</p>

                                </div>

                            </div>
                        </div>
                        <div className="
                        
                            flex
                            gap-3
                            p-2
                            mb-20

                        ">
                            {

                                category.map(category =>(

                                    <div className="
                                    
                                        order
                                        rounded-xl
                                        pl-2
                                        pr-2
                                        border-neutral-500
                                        border
                                    
                                    ">
                                        <p>{category.category}: ${category.avprice}</p>
                                    </div>

                                ))

                            }
                        </div>
                        <p className="
                        
                            text-neutral-500
                        
                        ">average price per 10 photos</p>
                    </div>
                </div>
                <div className="

                    grid
                    grid-cols-2
                    md:grid-cols-3
                    lg:grid-cols-4
                    gap-6
                    p-6
                    w-6/10
                    border
                    border-neutral-300
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    rounded-md
                    mt-10
                    mb-10
                    shrink-0

                ">

                    {albums?.map(album => (

                        <Link to={`/album/${album.albumid}/${album.albumname}`} key={album.albumid} className="
                        
                            bg-neutral-200
                            w-full
                            aspect-square
                            rounded-xl
                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                            hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                            transition
                            duration-100
                            active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                            relative
                            overflow-hidden

                        ">

                            <img src={"/api" + album.thumnail} className="
                            
                                absolute
                                w-full
                                h-full
                                object-cover
                                rounded-xl
                                top-0
                                left-0
                            
                            "/>

                            <div className="
                            
                                absolute
                                bottom-0
                                left-0
                                w-full
                                bg-black/50
                                p-3

                            ">
                                <p className="
                                
                                    text-white
                                    text-lg
                                    font-semibold
                                
                                ">{album.albumname}</p>
                            </div>

                        </Link>

                    ))}

                </div>
            </div>
        </div>

    )

}


export default PhotographerUser
