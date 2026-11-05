
import PHeader from "../compnents/pheader"
import { Link } from "react-router-dom"
import user from "../resources/user.png"
import calendar from "../resources/calendar (3).png"
import pin from "../resources/location.png"
import clock from "../resources/clock.png"
import image from "../resources/image.png"
import quote from "../resources/quote.png"
import phone from "../resources/phone-svgrepo-com.svg"
import mail from "../resources/email.png"
import { useLocation } from "react-router-dom"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt"

function Booking(){

    const location = useLocation()
    const booking = location.state.booking
    const [userProfile, setUserProfile] = useState("")

    const jwt = new jwtService()

    useEffect(()=>{

        fetch("/api/auth/getuserbyid",{

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },            
            body:JSON.stringify({

                userId:booking.clientid

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode == 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/auth/getuserbyid",{

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },            
                            body:JSON.stringify({

                                userId:booking.clientid

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                console.log(d.data)
                                setUserProfile(d.data)

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

                console.log(data.data)
                setUserProfile(data.data)

            }

        })

    }, [])

    function calcTime(slotNumb){

        if(slotNumb == 0){

            return "00:00"

        }
        if(slotNumb == 48){

            return "00:00"

        }
        if(slotNumb % 2 == 0){

            return slotNumb / 2 + ":00"

        }
        else{

            return (slotNumb - 1) / 2 + ":30"

        }

    }

    function calcDur(diff){

        let hours = 0
        let halfHour = null

        if(diff % 2 == 0){

            hours = diff / 2
            halfHour = false

        }
        else{

            hours = (diff - 1) / 2
            halfHour = true

        }

        if(hours == 1){

            return "1 hour"

        }
        else if(hours == 0 )

        if(halfHour){

            return hours + " and a half hours" 

        }else{

            return hours + " hours"

        }

    }


    return(

        <div className="
        
            w-screen
            h-screen
            overflow-hidden
            flex
            items-center
            flex-col
        
        ">
            <PHeader active={"Clients"}/>

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
                    h-100
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

                            " src={user} /> 

                            <div className="
                            
                                ml-5
                                mt-2
                            
                            ">
                                <p className="
                                
                                    font-bold
                                
                                ">{userProfile.firstname} {userProfile.lastname}</p>

                                <p className="
                                
                                    text-neutral-600
                                
                                ">user tag</p>
                            </div>
                            
                        </div>

                        <p className="
                        
                            bg-amber-300
                            h-7
                            w-[10vh]
                            flex
                            items-center
                            justify-center
                            rounded-xl
                            mt-2
                            mr-7

                            xl:w-[15vh]
                        
                        ">pending</p>
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
                                flex-col
                                gap-4
                            
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
                                    <p>{booking.dayofshoot?.split("-").reverse().join("-")}</p>
                                </div>

                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                    
                                        w-[2vh]
                                        h-[2vh]

                                    " src={pin} />

                                    <p>Greenwhich 1.1mil</p>
                                </div>
                            </div>

                            <div className="
                            
                                flex
                                flex-col
                                gap-4
                            
                            ">
                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                    
                                        w-[2vh]
                                        h-[2vh]

                                    " src={clock} />

                                    <p>{calcTime(booking.startslot)} - {calcTime(booking.endslot)} ({calcDur(booking.endslot - booking.startslot)})</p>
                                </div>

                                <div className="
                                
                                    flex
                                    items-center
                                    gap-2
                                
                                ">
                                    <img className="
                                    
                                        w-[2vh]
                                        h-[2vh]

                                    " src={image} />

                                    <p>wedding</p>
                                </div>
                            </div>
                        </div>

                        <div className="
                        
                            rounded-2xl
                            w-full
                            flex-1
                            bg-neutral-200
                            p-3
                            flex

                        ">
                            <img className="
                            
                                w-[3vh]
                                h-[3vh]
                                object-cover
                                opacity-50
                                
                                xl:w-[8vh]
                                xl:h-[8vh]

                            " src={quote} />

                            <p className="
                            
                                w-full
                                pr-20
                                ml-10
                                flex
                                items-center
                                tracking-wider
                                font-semibold
                                text-[2vh]
                                text-neutral-800

                                xl:text-[2vh]
                            
                            ">Hi mark, was wondering if you would be free to do some wedding photogrpahy for my upcomming wedding</p>
                        </div>
                    </div>
                </div>

                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-50
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-5
                    justify-self-center
                    shrink-0
                
                ">
                    <div className="
                    
                        flex
                        w-full
                        justify-between
                    
                    ">
                        <p className="
                        
                            font-semibold
                            pl-5
                            pt-3
                            text-[2vh]
                        
                        ">Clients Details</p>

                        <Link className="
                        
                            pr-5
                            pt-3
                            text-neutral-600
                            hover:text-black
                            active:text-neutral-600

                        
                        ">See profile</Link>
                    </div>

                    <div className="
                    
                        flex
                        w-full
                        justify-start
                        p-5
                    
                    ">
                        <div className="
                        
                            flex
                            flex-col
                            gap-4
                        
                        ">
                            <div className="
                            
                                flex
                                items-center
                                gap-2
                            
                            ">
                                <img className="
                                
                                    w-[3vh]
                                    h-[3vh]
                                
                                " src={user} />

                                <p>{userProfile.firstname} {userProfile.lastname}</p>
                            </div>

                            <div className="
                            
                                flex
                                items-center
                                gap-2
                            
                            ">
                                <img className="
                                
                                    w-[3vh]
                                    h-[3vh]
                                
                                " src={phone} />

                                <p>+44 74165691</p>
                            </div>

                            <div className="
                            
                                flex
                                items-center
                                gap-2
                            
                            ">
                                <img className="
                                
                                    w-[3vh]
                                    h-[3vh]
                                
                                " src={mail} />

                                <p>{userProfile.email}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-100
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-5
                    justify-self-center
                    shrink-0
                
                ">
                    <p className="
                    
                        w-full
                        font-semibold   
                        pl-5
                        pt-3
                        text-[2vh]

                        xl:text-[3vh]
                            
                    ">Booking Form</p>
                </div>
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-6/10
                    h-30
                    mb-5
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-row
                    justify-evenly
                    
                    items-center
                    rounded-md
                    mt-5
                    justify-self-center
                    shrink-0
                
                ">

                    <button className="
                        
                        bg-green-600
                        text-white
                        rounded-xl
                        w-[10vh]
                        h-8.75
                        flex
                        items-center
                        justify-center
                         hover:bg-green-900
      
                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        xl:h-[7vh]
                        xl:w-[30vh]
                    
                        
                    
                    ">
                        Accept
                    </button>
                    <button className="
                        
                        bg-red-600
                        text-white
                        rounded-xl
                        w-[10vh]
                        h-8.75
                        flex
                        items-center
                        justify-center
                        hover:bg-red-800
      
                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        xl:h-[7vh]
                        xl:w-[30vh]
                    
                        
                    
                    ">
                        Reject
                    </button>
                
                </div>
            </div>
        </div>

    )

}

export default Booking
