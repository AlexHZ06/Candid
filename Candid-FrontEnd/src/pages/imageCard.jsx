import CHeader from "../compnents/cheader"
import { useLocation } from "react-router-dom"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt"
import price from "../resources/icons8-price-50.png"
import phone from "../resources/phone-svgrepo-com.svg"
import mail from "../resources/email.png"
import { Link } from "react-router-dom"
import { useNavigate } from "react-router-dom"

function ImageCard() {

    const location = useLocation()
    const image = location.state.photo
    const profile = location.state.profile
    console.log(image)
    const jwt = new jwtService()
    const [user, setUser] = useState()
    const [category, setCategory] = useState([])
    const [catPrice, setCatPrice] = useState()
    const navigate = useNavigate()

    useEffect(() => {

        let categorys = image.category.split(",")
        setCategory(categorys)

        fetch("/api/auth/getuserbyid", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                userId:image.userid

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()

                    if(result){

                        fetch("/api/auth/getuserbyid", {

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                userId:image.userid

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                setUser(d.data)
                                console.log(d.data)

                            }
                            else{

                                console.log("error")

                            }

                        })

                    }

                }else{

                    console.log("error")

                }

            }else{

                setUser(data.data)
                console.log(data.data)

            }

        })

        fetch("/api/image/getcatprice", {

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                category:profile.projectcatagory,
                userId:image.userid

            })




        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/image/getcatprice", {

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                category:profile.projectcatagory,
                                userId:image.userid

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                setCatPrice(d.data)

                            }
                            else{

                                console.log("error")

                            }

                        })      

                    }
                         
                }else{

                    console.log("error")
                    
                }
            }
            else{

                setCatPrice(data.data)

            }

        })

    }, [])

    return (
        <div className="
            w-screen
            min-h-screen
            flex
            flex-col
            items-center
        ">
            <CHeader active="Search"/>

            <div className="
                w-8/10 
                mt-10
            ">
                <div className="
                        flex 
                        w-full 
                        gap-6
                ">

                    <div className="flex-1">
                        <img
                            src={image.photourl}
                            className="
                            
                                w-full 
                                h-150
                                p-5
                                object-contain
                                border
                                border-neutral-300                            
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                rounded-md
                            
                            "
                        />
                    </div>

                    <div className="
                        flex-1
                        flex
                        flex-col
                        p-6
                        h-150                                                    
                        border
                        border-neutral-300                            
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        rounded-md
                        mb-3
                        
                    ">
                        <div className="
                        
                            flex
                            flex-col
                            border-b
                            pb-3
                            border-neutral-600

                        ">
                            <div className="
                            
                                border-b
                                pb-2
                                border-neutral-600
                                flex
                                justify-between
                            
                            ">
                                <div>
                                    <p className="
                                    
                                        text-2xl 
                                        font-bold

                                    ">
                                        {image.photoname}
                                    </p>
                                    <p>
                                        by {user?.firstname} {user?.lastname} : {image.dateposted}
                                    </p>
                                </div>
                                <div>
                                    <button className="
                                    
                                        text-neutral-600
                                        hover:text-black
                                        active:text-neutral-600
                                    
                                    ">See profile</button>
                                </div>
                            </div>
                            <div className="
                            
                                flex
                                flex-row
                                gap-2
                                mt-3
                            
                            ">
                                {category.map(category => (

                                    <div className={`
                                    
                                        border
                                        rounded-xl
                                        pl-2
                                        pr-2
                                        border-neutral-500
                                        ${category == profile.projectcatagory.toLowerCase() ? "bg-amber-400 border-0" : ""}
                                    
                                    `}>
                                        {category}
                                    </div>

                                ))}
                            </div>
                            <p className="
                                
                                mt-4
                                

                            ">
                                {image.description}
                            </p>
                        </div>
                        <div className="

                            flex 
                            gap-3
                            pt-5

                        ">
                            <div className="

                                flex 
                                items-center 
                                gap-2
                                border
                                rounded-xl
                                pl-3
                                pr-3
                                border-neutral-500
                                

                            ">
                                <img className="

                                    w-[2vh] 
                                    h-[2vh]

                                " 
                                src={phone} />
                                <p>{user?.phone}</p>
                            </div>

                            <div className="

                                flex 
                                items-center 
                                gap-2
                                rounded-xl
                                pl-3
                                pr-3
                                border-neutral-500
                                border

                            ">
                                <img className="

                                    w-[2vh] 
                                    h-[2vh]

                                " 
                                src={mail} />
                                <p>{user?.email}</p>
                            </div>
                            <div className="

                                flex 
                                items-center 
                                gap-2
                                rounded-xl
                                pl-3
                                pr-3
                                border-neutral-500
                                border

                            ">
                                <img className="

                                    w-[2vh] 
                                    h-[2vh]

                                " 
                                src={price} />
                                <p>${catPrice} for 10 photos avg</p>
                            </div>
                        </div>
                        <div className="
                        
                            h-full
                            flex
                            flex-co
                            justify-end
                            items-center
                                
                        ">
                            <div className="
                            
                                w-full
                                h-full
                                flex
                                gap-10
                                items-end
                                justify-center
                            
                            ">
                                <Link to={`/makebooking/${image.userid}`} className="
                            
                                    bg-black
                                    text-white
                                    rounded-xl
                                    w-[20vh]
                                    h-10
                                    flex
                                    items-center
                                    justify-center
                                    hover:bg-gray-900
        
                                    transition
                                    duration-100

                                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                    active:bg-white
                                    active:text-black

                                "
                                >Book Photographer</Link>
                                <button onClick={() => {

                                    navigate(-1)
                                    
                                }} className="
                            
                                    bg-black
                                    text-white
                                    rounded-xl
                                    w-[20vh]
                                    h-10
                                    flex
                                    items-center
                                    justify-center
                                    hover:bg-gray-900
        
                                    transition
                                    duration-100

                                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                    active:bg-white
                                    active:text-black

                                "
                                >back</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ImageCard