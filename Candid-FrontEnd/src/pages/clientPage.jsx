import { use, useEffect, useState } from "react"
import PHeader from "../compnents/pheader"
import Radial from "../compnents/radial"
import { Link } from "react-router-dom"
import { jwtService } from "../logic/jwt"



function ClientPage() {

    const [active, setActive] = useState("All Clients")
    const jwt = new jwtService()
    const [bookings, setBookings] = useState([])
    const [clients, setClients] = useState([])
    const dict = Object.fromEntries(
        clients.map(client => [
            client.userid,
            client.firstname + " " + client.lastname
        ])
    )
    const [searchText, setSearchText] = useState("")
    

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

    useEffect(()=>{

        fetch("/api/booking/getbookingslots",{

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                userId:jwt.decodeUserId()

            })

        }).then(Response => Response.json()).then( async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/booking/getbookingslots",{

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                userId:jwt.decodeUserId()

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                setBookings(d.data)

                            }

                        })     

                    }

                }

            }
            else{

                setBookings(data.data)

            }

        })

    }, [])

    useEffect(()=>{

        console.log(bookings)
        if(bookings.length > 0){

            let idList = []
            for(let i = 0; i < bookings.length; i++){

                if(!idList.includes(bookings[i].clientid)){

                    idList.push(bookings[i].clientid);

                }

            }

            fetch("/api/auth/getuserbyids", {

                method:"POST",

                headers:{

                    "Content-Type": "application/json",
                    auth: localStorage.getItem("jwt")

                },
                body:JSON.stringify({

                    userIds:idList

                })

            }).then(response => response.json()).then(async data => {

                if(!data.success){

                    if(data.internalCode == 401){

                        let result = await jwt.requestJwt()
                        if(result){

                            fetch("/api/auth/getuserbyids", {

                                method:"POST",

                                headers:{

                                    "Content-Type": "application/json",
                                    auth: localStorage.getItem("jwt")

                                },
                                body:JSON.stringify({

                                    userIds:idList

                                })

                            }).then(r => r.json()).then(d => {

                                if(d.success){

                                    setClients(d.data)

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

                    setClients(data.data)

                }

            })

        }

        

    }, [bookings])


    function radialClick(text){

        if(text == active){

            setActive("All Clients")

        }
        else{

            setActive(text)

        }

    }

    const matchingBookings = bookings.filter(booking => {
        if (active == "Pending" && booking.status != "pending") return false
        if (active == "Accepted" && booking.status != "accepted") return false
        if (active == "Completed" && booking.status != "completed") return false
        if (active == "Cancelled" && booking.status != "cancelled") return false
        return true
    })

    return (
        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <PHeader active="Clients" />
            <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-7/10
                    h-[80vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                    justify-self-center
                    

                ">
                <div className="
                    
                        h-2/10
                        w-9/10
                        flex
                        flex-row
                        items-center
                        justify-evenly
                        mt-5
                    
                    ">
                    <div className="
                        
                        
                        
                        ">
                        <p className="
                            
                                font-light
                                text-2xl
                            
                            ">Total Clients</p>
                        <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">5</p>
                    </div>
                    <div>
                        <p className="
                            
                                font-light
                                text-2xl
                            
                            ">Pending Requests</p>
                        <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">3</p>
                    </div>
                    <div>
                        <p className="
                            
                                font-light
                                text-2xl
                            
                            ">upcomming sessions</p>
                        <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">2</p>
                    </div>
                    <div>
                        <p className="
                            
                                font-light
                                text-2xl
                            
                            ">galleries deleivered</p>
                        <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">5</p>
                    </div>
                </div>
                <div className="
                    border-t-1
              
                    border-neutral-400
                    w-9/10
                    mb-5
                    pt-4
                    
                ">
                    <div>
                        <div className="
                    
                            flex
                            gap-2
                            justify-center
                            items-center
                            flex-col

                        ">
                            <div className="

                                flex 
                                gap-2
                                mb-3
                                mt-2

                            ">
                                <input onChange={(e) => setSearchText(e.target.value)} placeholder="Search by client by name" type="text" className="
                        
                                    border-2
                                    border-neutral-300
                                    rounded-4xl
                                    pl-2
                                    w-[90vh]
                                    h-[4vh]

                                    hover:border-neutral-400
                                    active:border-neutral-500
                                    hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                                    focus:ring-0
                                    
                                "/>
                            </div>
                        </div>
                    </div>
                    <div className="
                    
                        
                        h-1/10
                        flex
                        items-center
                        justify-evenly
                        w-[90vh]
                        justify-self-center
                        mt-3
                        
                    
                    ">
                        <Radial onClick={()=>{radialClick("All Clients")}} active={active} text="All Clients"/>
                        <Radial onClick={()=>{radialClick("Pending")}} text="Pending" active={active}/>
                        <Radial onClick={()=>{radialClick("Accepted")}} text="Accepted" active={active}/>
                        <Radial onClick={()=>{radialClick("Completed")}} text="Completed" active={active}/>
                        <Radial onClick={()=>{radialClick("Cancelled")}} text="Cancelled" active={active}/>

                        <select onChange={(e) => setActiveTag(e.target.value)} defaultValue={""} name="" className="
                            border
                            border-neutral-400
                            w-fit
                            pl-3
                            pr-3
                            rounded-xl
                            whitespace-nowrap
                            hover:shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        ">
                            <option disabled value="">Order By</option>
                            <option value="Newest">Newest</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Urgency">Urgency</option>                      
                        </select>
                    </div>
                </div>
                <div className="
                
                    w-9/10
                    h-full
                    mb-10
                    flex
                    flex-col
                    items-center
                    overflow-y-scroll
                    gap-5
                    pt-3
                    shadow-[2_0_5px_rgba(0,0,0,0.25)_inset]
                
                ">
                    
                    {matchingBookings.length == 0 && (
                        <p className="text-gray-500 text-lg mt-5">
                            No bookings match your requirements.
                        </p>
                    )}

                    {bookings.map(booking => (

                        <Link id={booking.slotid} className={`
                        
                            w-8/10
                            h-20
                            shrink-0
                            rounded-2xl
                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                            flex
                            items-center
                            hover:shadow-[0_0px_10px_rgba(0,0,0,0.35)]
                            active:shadow-[0_0px_4px_rgba(0,0,0,0.2)_inset]
                            transition-all
                            duration-300
                            ${active == "Pending" && booking.status != "pending" ? "hidden": ""}
                            ${active == "Accepted" && booking.status != "accepted" ? "hidden": ""}
                            ${active == "Completed" && booking.status != "completed" ? "hidden": ""}
                            ${active == "Cancelled" && booking.status != "cancelled" ? "hidden": ""}
                            ${searchText.length > 0 && !dict[booking.clientid].toLowerCase().includes(searchText.toLowerCase()) ? "hidden" : ""}
                        
                        `}>
                            <img className="
                            
                                h-15
                                bg-blue-300
                                rounded-4xl
                                ml-5
                                flex-1
                                
                            
                            " src="src\resources\user.png" />
                            <div className="
                            
                                ml-5
                                flex-10

                            ">
                                <div className="
                                
                                    flex
                                    tracking-wide
                         
                                    gap-6
                                
                                ">
                                    <p className="
                                    
                                        tracking-wide
                                        font-bold
                                    
                                    ">{dict[booking.clientid]}</p>
                                    <p className="
                                    
                                        text-neutral-600
                                    
                                    ">{booking.dayofshoot}</p>
                                    <p className="
                                    
                                        text-neutral-600
                                    
                                    ">{calcTime(booking.startslot)} - {calcTime(booking.endslot)}</p>
                                    
                                </div>
                                <p>tag line</p>
                            </div>
                            <div className="
                            
                                flex-10
                            
                            ">

                            </div>
                            <div className={`
                                flex-2
                                mr-5
                                rounded-2xl
                                w-[8vh]
                                h-7
                                flex
                                items-center
                                justify-center
                                ${booking.status === "accepted" ? "bg-green-400":""}
                                ${booking.status === "pending" ? "bg-amber-300":""}
                            
                            `}>
                                <p>{booking.status}</p>
                            </div>
                        </Link>

                    ))}

                </div>
            </div>
        </div>
    )

}

export default ClientPage