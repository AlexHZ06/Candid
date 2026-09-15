import PHeader from "../compnents/pheader"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt"
import { Navigate, useNavigate } from "react-router-dom"

function EditSchedule(){

    const [scheduleSlots, setScheduleSlots] = useState(() => {
        const slots = []

        for (let i = 0; i < 7; i++) {
            const week = []

            for (let j = 0; j < 48; j++) {
                week.push({
                    startslot: j,
                    endslot: j,
                    dayofweek: i,
                    status: ""
                })
            }

            slots.push(week)
        }

        return slots
    })
    const photographerId = 2
    const tokenService = new jwtService()
    const [retrievedSchedule, setRetrievedSchedule] = useState([])
    const navigate = useNavigate()
    const [selected, setSelected] = useState([])
    const [errorText, setErrorText] = useState("")

    useEffect(() => {

        tokenService.checkTokenPhotographer()

        fetch("/api/booking/getschedule", {

            method:"POST",
            headers:{

                "Content-Type":"application/json",
                auth:localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                userId:photographerId

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await tokenService.requestJwt()
                    if(result){

                        fetch("/api/booking/getschedule", {

                            method:"POST",
                            headers:{

                                "Content-Type":"application/json",
                                auth:localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                userId:photographerId

                            })

                        }).then(r => r.json()).then(d => {

                            if(!d.success){

                                setErrorText(d.error)

                            }
                            else{

                                setRetrievedSchedule(d.data)
                                console.log(d.data)

                            }
                            
                        })

                    }
                    else{

                        navigate("/rejectedjwt")

                    }

                }else{

                    setErrorText(data.error)
                    
                }

            }else{

                setRetrievedSchedule(data.data)
                console.log(data.data)

            }

        })

    }, [])

    useEffect(() => {
        if (retrievedSchedule.length === 0) {
            return
        }

        setScheduleSlots(prev => {
            const updated = prev.map(day => [...day])

            for (let i = 0; i < retrievedSchedule.length; i++) {
                let slot = retrievedSchedule[i].startslot
                const end = retrievedSchedule[i].endslot
                const day = retrievedSchedule[i].dayofweek

                while (slot <= end) {

                    updated[day][slot] = {...updated[day][slot], status: "schedule"}
                    slot++
                }
            }

            return updated
        })
    }, [retrievedSchedule])

    function revert(){

        setScheduleSlots(() => {
        const slots = []

        for (let i = 0; i < 7; i++) {
            const week = []

            for (let j = 0; j < 48; j++) {
                week.push({
                    startslot: j,
                    endslot: j,
                    dayofweek: i,
                    status: ""
                })
            }

            slots.push(week)
        }

        return slots
        })

        if (retrievedSchedule.length === 0) {
            return
        }

        setScheduleSlots(prev => {
            const updated = prev.map(day => [...day])

            for (let i = 0; i < retrievedSchedule.length; i++) {
                let slot = retrievedSchedule[i].startslot
                const end = retrievedSchedule[i].endslot
                const day = retrievedSchedule[i].dayofweek

                while (slot <= end) {

                    updated[day][slot] = {...updated[day][slot], status: "schedule"}
                    slot++
                }
            }

            return updated
        })

    }

    useEffect(() => {

        console.log(scheduleSlots)

    }, [scheduleSlots])

    function selectSlot(status, startSlot, day) {

        setScheduleSlots(prev => {

            const updated = prev.map(daySlots => [...daySlots])

            for (let i = 0; i < prev.length; i++) {

                for (let j = 0; j < prev[i].length; j++) {

                    if ( prev[i][j].startslot === startSlot && prev[i][j].dayofweek === day){

                        if(status === "selected" || status === "schedule"){

                            updated[i][j] = {...updated[i][j],status: ""}

                        }
                        else{

                            updated[i][j] = {...updated[i][j],status: "selected"}

                        }

                        return updated
                    }
                }
            }


            return updated
        })
    }

    function submit(){

        let slotList = []

        for(let i = 0; i < scheduleSlots.length; i++){

            let temp = []

            for(let j = 0; j < scheduleSlots[i].length; j++){


                if(scheduleSlots[i][j].status === "selected" || scheduleSlots[i][j].status === "schedule"){
                    
                    temp.push({

                        scheduleid:0,
                        startslot:scheduleSlots[i][j].startslot,
                        endslot:scheduleSlots[i][j].endslot,
                        dayofweek:scheduleSlots[i][j].dayofweek,
                        photographerid:0

                    })

                    

                }

            }
            slotList.push(temp)
        }

        let newSlots = []

        for(let i = 0; i < slotList.length; i++){

            if(slotList[i].length === 0){

                continue

            }

            for(let j = 0; j < slotList[i].length; j++){

                const current = slotList[i][j].startslot

                const hasPrevious = j > 0 && current - slotList[i][j - 1].startslot === 1

                const hasNext = j < slotList[i].length - 1 && slotList[i][j + 1].startslot - current === 1

                if(!hasPrevious && !hasNext){
                    
                    setErrorText("Cannot select a single slot")
                    return
                }

            }

            let consec = false
            let start = slotList[i][0].startslot
            let end = 0

            for(let j = 1; j < slotList[i].length; j++){

                if(slotList[i][j] - slotList[i][j - 1] === 1){

                    consec = true
                    end = slotList[i][j]

                }
                else{

                    if(consec){

                        newSlots.push({

                            scheduleid:0,
                            startslot:start,
                            endslot:end,
                            dayofweek:i,
                            photographerid:0
                            
                        })
                        consec = false

                    }
                    if(!consec){

                        start = slotList[i][j].startslot

                    }

                }

                if(consec){

                    newSlots.push({

                        scheduleid:0,
                        startslot:start,
                        endslot:end,
                        dayofweek:i,
                        photographerid:0
                                
                    })     

                }

            }

        }       

        fetch("/api/booking/photographer/saveschedule",{

            method:"POST",
            headers:{

                "Content-Type":"application/json",
                auth:localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                data:slotList

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await tokenService.requestJwt()
                    if(result){

                         fetch("/api/booking/photographer/saveschedule",{

                            method:"POST",
                            headers:{

                                "Content-Type":"application/json",
                                auth:localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                               data:slotList

                            })

                        }).then(r => r.json()).then(d => {

                            if(!d.success){

                                   setErrorText(d.error)

                             }
                            else{

                                 console.log(d.data)

                            }
                                
                        })                     

                    }

                }else{

                    setErrorText(data.error)
                        
                }

            }else{

                console.log(data.data)

            }

        })

    }

    return(

        <div className="
        
            w-screen 
            min-h-screen 
            flex
            flex-col
            items-center
        
        ">
            <PHeader active={"Calander"}/>
            <div className="
            
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                w-[150vh]
                rounded-xl
                border-neutral-300
                h-[80vh]
                mt-10
                flex
                flex-col
                items-center
                
                

            ">
                <div className="
                    

                    w-[130vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    h-[60vh]
                    rounded-xl
                    mt-10
                    overflow-y-auto
                    overflow-x-hidden
                    p-2

                    
                  
                ">
                    <div className="
                    
                        flex

                        justify-evenly  
                        border-t
                        border-b
                        border-r
                    
                    ">
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center

                        
                        ">
                            Time
                            <div className="
                            
                                border-t
                                w-10/10
                                flex
                                items-center
                                flex-col

                            
                            ">
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">00:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">00:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">01:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">01:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">02:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">02:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">03:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">03:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">04:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">04:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">05:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">05:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">06:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">06:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">07:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">07:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">08:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">08:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">09:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">09:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">10:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">10:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">11:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">11:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">12:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">12:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">13:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">13:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">14:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">14:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">15:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">15:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">16:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">16:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">17:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">17:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">18:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">18:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">19:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">19:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">20:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">20:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">21:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">21:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">22:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">22:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">23:00</p>
                                <p className="
                                
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">23:30</p>
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Monday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[0]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}

                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Tuesday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[1]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Wednesday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[2]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Thursday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[3]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Friday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[4]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Saturday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[5]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Sunday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[6]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                    </div>
                </div>
                <p className="
                
                    pt-10
                    text-red-600
                
                ">{errorText}</p>
                <div className="
                
                    flex
                    flex-row
                    gap-3
                    mt-10
                    xl:mt-5
                    xl:mb-5
                
                ">
                    <button onClick={submit} className="
                    
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
                    
                    ">Save</button>
                    <button onClick={revert} className="
                    
                        bg-red-600
                        text-white
                        rounded-xl
                        w-[10vh]
                        h-8.75
                        flex
                        items-center
                        justify-center
                         hover:bg-red-700
      
                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black
                    
                    ">Revert</button>
                    <button className="
                    
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
                    
                    ">Back</button>
                </div>

            </div>
        </div>

    )

}

export default EditSchedule